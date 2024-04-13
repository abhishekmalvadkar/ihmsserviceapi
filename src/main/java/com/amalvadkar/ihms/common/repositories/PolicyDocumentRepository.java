package com.amalvadkar.ihms.common.repositories;

import com.amalvadkar.ihms.common.entities.PolicyDocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyDocumentRepository extends JpaRepository<PolicyDocumentEntity, Long> {
}
