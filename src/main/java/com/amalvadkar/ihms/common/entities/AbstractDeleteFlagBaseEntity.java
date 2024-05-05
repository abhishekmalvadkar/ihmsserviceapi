package com.amalvadkar.ihms.common.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractDeleteFlagBaseEntity {

    @Column(name = "delete_flag", nullable = false)
    private boolean deleteFlag;
}
