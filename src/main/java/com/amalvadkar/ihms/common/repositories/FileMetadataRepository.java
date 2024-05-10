package com.amalvadkar.ihms.common.repositories;

import com.amalvadkar.ihms.common.entities.FileMetadataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileMetadataRepository extends JpaRepository<FileMetadataEntity, String> {
    Optional<FileMetadataEntity> findByIdAndDeleteFlagIsFalse(String fileId);

    List<FileMetadataEntity> findAllByRecordId(String recordId);
}
