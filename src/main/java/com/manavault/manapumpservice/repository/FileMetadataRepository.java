package com.manavault.manapumpservice.repository;

import com.manavault.manapumpservice.model.FileMetadata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {
    Optional<FileMetadata> findTopByOrderByDownloadTimeDesc();
}