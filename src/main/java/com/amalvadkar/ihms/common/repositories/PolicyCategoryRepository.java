package com.amalvadkar.ihms.common.repositories;

import com.amalvadkar.ihms.common.entities.PolicyCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyCategoryRepository extends JpaRepository<PolicyCategoryEntity, Long> {
}
