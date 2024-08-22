package com.manavault.manapumpservice.service;

import com.manavault.manapumpservice.model.FileMetadata;
import com.manavault.manapumpservice.repository.FileMetadataRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileDownloadService {

    private final FileMetadataRepository fileMetadataRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    private final ObjectMapper objectMapper = new ObjectMapper();
    public FileMetadata downloadDefaultCardsFile() throws IOException {
        // Fetch bulk data from Scryfall API
        String scryfallBulkDataUrl = "https://api.scryfall.com/bulk-data";
        Map<String, Object> bulkDataResponse = restTemplate.getForObject(scryfallBulkDataUrl, Map.class);

        // Extract the download URI for "Default Cards"
        List<Map<String, Object>> dataList = (List<Map<String, Object>>) bulkDataResponse.get("data");
        String defaultCardsDownloadUrl = dataList.stream()
                .filter(item -> "default_cards".equals(item.get("type")))
                .map(item -> (String) item.get("download_uri"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Default Cards download URL not found"));

        // Get the file metadata with the most recent download time
        Optional<FileMetadata> optionalFileMetadata = fileMetadataRepository.findTopByOrderByDownloadTimeDesc();
        if (optionalFileMetadata.isPresent()) {
            FileMetadata latestFileMetadata = optionalFileMetadata.get();
            // Check if the fileUrl matches and if it's been at least 6 hours since the last download
            LocalDateTime sixHoursAgo = LocalDateTime.now().minusHours(6);
            if (latestFileMetadata.getFileUrl().equals(defaultCardsDownloadUrl) && latestFileMetadata.getDownloadTime().isAfter(sixHoursAgo)) {
                // If it's been less than 6 hours, return the existing file metadata
                System.out.println("File already downloaded less than 6 hours ago. Returning latest file metadata.");
                return latestFileMetadata;
            }
        }


        // Download the file
        return downloadFile(defaultCardsDownloadUrl);
    }

    private FileMetadata downloadFile(String fileUrl) throws IOException {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1);
        String localDir = "downloaded";
        File directory = new File(localDir);
        if (!directory.exists()) {
            directory.mkdir();
        }

        String localPath = Paths.get(localDir, fileName).toString();

        try (FileOutputStream fos = new FileOutputStream(localPath)) {
            URL url = new URL(fileUrl);
            fos.write(restTemplate.getForObject(url.toString(), byte[].class));
        }

        FileMetadata metadata = new FileMetadata();
        metadata.setFileName(fileName);
        metadata.setFileUrl(fileUrl);
        metadata.setLocalPath(localPath);
        metadata.setDownloadTime(LocalDateTime.now());
        metadata.setStatus("Downloaded");

        return fileMetadataRepository.save(metadata);
    }
}
