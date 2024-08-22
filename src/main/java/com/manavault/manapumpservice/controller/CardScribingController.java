package com.manavault.manapumpservice.controller;
import com.manavault.manapumpservice.model.FileMetadata;
import com.manavault.manapumpservice.repository.FileMetadataRepository;
import com.manavault.manapumpservice.service.CardScribingService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CardScribingController {

    private final CardScribingService cardScribingService;
    private final FileMetadataRepository fileMetadataRepository;

    @PostMapping("/api/cards/load")
    public String loadCards() {
        try {
            Optional<FileMetadata> optionalFileMetadata = fileMetadataRepository.findTopByOrderByDownloadTimeDesc();
            if (optionalFileMetadata.isPresent()) {
                String filePath = optionalFileMetadata.get().getLocalPath();
                cardScribingService.saveCardsFromJsonFile(filePath);
                return "Cards loaded successfully.";
            } else {
                return "No file metadata found.";
            }
        } catch (IOException e) {
            return "Failed to load cards: " + e.getMessage();
        }
    }
}
