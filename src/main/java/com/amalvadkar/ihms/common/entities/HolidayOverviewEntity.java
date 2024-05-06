package com.amalvadkar.ihms.common.entities;

import com.amalvadkar.ihms.common.enums.OptionalStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "holiday_overview")
@Getter
@Setter
public class HolidayOverviewEntity extends AbstractIdBaseEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "holiday_date", nullable = false)
    private LocalDate holidayDate;

    @Column(name = "is_optional", nullable = false)
    @Enumerated(EnumType.STRING)
    private OptionalStatusEnum isOptional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private CountryEntity countryEntity;

}
