package com.amalvadkar.ihms.common.repositories;

import com.amalvadkar.ihms.common.entities.PolicyCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PolicyCategoryRepository extends JpaRepository<PolicyCategoryEntity, Long> {

    @Query("""
            SELECT pce FROM PolicyCategoryEntity pce
            WHERE pce.deleteFlag = false
            ORDER By pce.displayOrder ASC
            """)
    List<PolicyCategoryEntity> findAllPolicyCategories();

}
