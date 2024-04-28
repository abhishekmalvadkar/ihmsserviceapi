package com.amalvadkar.ihms.common.repositories;

import com.amalvadkar.ihms.common.entities.HolidayOverviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HolidayOverviewRepository extends JpaRepository<HolidayOverviewEntity, Long> {

    @Query("""
            SELECT ho FROM HolidayOverviewEntity ho
            JOIN FETCH ho.countryEntity c
            WHERE c.id = :countryId
            AND c.deleteFlag = false
            ANd ho.deleteFlag = false
            """)
    List<HolidayOverviewEntity> findHolidaysBasedOnCountryId(@Param("countryId") Long countryId);

}
