package com.amalvadkar.ihms.common.entities;

import com.amalvadkar.ihms.common.enums.CategoryEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "files_metadata")
@Getter
@Setter
public class FileMetadataEntity extends AbstractStringIdBaseEntity  {

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryEnum categoryEnum;

    @Column(name = "record_id", nullable = false)
    private String recordId;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "path", nullable = false)
    private String path;

}
