package com.manavault.manapumpservice.controller;

import com.manavault.manapumpservice.model.FileMetadata;
import com.manavault.manapumpservice.service.FileDownloadService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileDownloadController {

    private final FileDownloadService fileDownloadService;

    @PostMapping("/download-default-cards")
    public ResponseEntity<FileMetadata> downloadDefaultCardsFile() {
        try {
            FileMetadata fileMetadata = fileDownloadService.downloadDefaultCardsFile();
            return ResponseEntity.ok(fileMetadata);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}