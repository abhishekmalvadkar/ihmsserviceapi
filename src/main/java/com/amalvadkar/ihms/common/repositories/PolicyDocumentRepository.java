package com.amalvadkar.ihms.common.repositories;

import com.amalvadkar.ihms.common.entities.PolicyDocumentEntity;
import com.amalvadkar.ihms.common.exceptions.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PolicyDocumentRepository extends JpaRepository<PolicyDocumentEntity, Long> {

    String ENTITY_NAME = "Policy document";
    String ID_PROPERTY_NAME = "id";

    @Query("""
            SELECT pde FROM PolicyDocumentEntity pde
            WHERE pde.policyCategoryEntity.id = :policyCategoryId
            AND pde.deleteFlag = false
            ORDER By pde.displayOrder ASC
            """)
    List<PolicyDocumentEntity> findDocumentsByPolicyCategoryId(
            @Param("policyCategoryId") Long policyCategoryId);


    @Modifying
    @Query("""
            UPDATE PolicyDocumentEntity pde SET pde.viewCount = pde.viewCount + 1
            WHERE pde.id = :policyDocumentId
            """)
    int increaseViewCount(@Param("policyDocumentId") Long policyDocumentId);

    default PolicyDocumentEntity findByIdOrThrow(Long policyDocumentId) {
        return findById(policyDocumentId)
                .orElseThrow(() ->
                        ResourceNotFoundException.
                                from(ENTITY_NAME, ID_PROPERTY_NAME, String.valueOf(policyDocumentId)));
    }

}
