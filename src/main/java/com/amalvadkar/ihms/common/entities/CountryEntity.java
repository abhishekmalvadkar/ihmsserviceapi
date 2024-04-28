package com.amalvadkar.ihms.common.entities;

import com.amalvadkar.ihms.common.enums.CountryIsoCodeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "country")
@Getter
@Setter
public class CountryEntity extends AbstractBaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "iso_code", nullable = false)
    @Enumerated(EnumType.STRING)
    private CountryIsoCodeEnum isoCode;

}
