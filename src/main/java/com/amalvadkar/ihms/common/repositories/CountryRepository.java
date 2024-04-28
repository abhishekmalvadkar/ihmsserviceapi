package com.amalvadkar.ihms.common.repositories;

import com.amalvadkar.ihms.common.entities.CountryEntity;
import com.amalvadkar.ihms.common.exceptions.ResourceNotFoundException;
import com.amalvadkar.ihms.common.models.response.KeyValueResModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CountryRepository extends JpaRepository<CountryEntity, Long> {

    String ENTITY_NAME = "Country";

    @Query("""
            SELECT
                new com.amalvadkar.ihms.common.models.response.KeyValueResModel
                (
                    c.id,
                    c.name   
                )
            FROM CountryEntity c
            WHERE c.deleteFlag = false
            """)
    List<KeyValueResModel> findCountryNamesWithIds();

    default Long findFirstCountryId(){
        return findCountryNamesWithIds()
                .stream()
                .map(KeyValueResModel::key)
                .findFirst()
                .orElseThrow(() -> ResourceNotFoundException.from(ENTITY_NAME));
    }

}
