package com.amalvadkar.ihms.common.repositories;

import com.amalvadkar.ihms.common.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
