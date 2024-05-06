package com.amalvadkar.ihms.common.entities;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractBaseEntity extends AbstractDeleteFlagBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private UserEntity createdBy;

    @Column(name = "created_on", nullable = false)
    private Instant createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private UserEntity updatedBy;

    @Column(name = "updated_on")
    private Instant updatedOn;


    @PrePersist
    public void prePersistForBaseEntity() {
        this.createdOn = Instant.now();
    }

    @PreUpdate
    public void preUpdateForBaseEntity() {
        this.updatedOn = Instant.now();
    }

}
