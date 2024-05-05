package com.amalvadkar.ihms.common.entities;

import com.github.f4b6a3.ulid.UlidCreator;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractStringIdBaseEntity extends AbstractBaseEntity {

    @Id
    @Column(name = "id")
    private String id;

    @PrePersist
    public void prePersistForStringIdBaseEntity() {
        this.id = UlidCreator.getUlid().toString();
    }
}
