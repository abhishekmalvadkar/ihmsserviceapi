package com.amalvadkar.ihms.common.repositories;

import com.amalvadkar.ihms.common.entities.FeedBackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedBackRepository extends JpaRepository<FeedBackEntity, String> {
}
