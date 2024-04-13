package com.amalvadkar.ihms.common.repositories;

import com.amalvadkar.ihms.common.entities.PolicyDocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PolicyDocumentRepository extends JpaRepository<PolicyDocumentEntity, Long> {

    @Query("""
            SELECT pde FROM PolicyDocumentEntity pde
            WHERE pde.policyCategoryEntity.id = :policyCategoryId
            AND pde.deleteFlag = false
            ORDER By pde.displayOrder ASC
            """)
    List<PolicyDocumentEntity> findDocumentsByPolicyCategoryId(
            @Param("policyCategoryId") Long policyCategoryId);

}
