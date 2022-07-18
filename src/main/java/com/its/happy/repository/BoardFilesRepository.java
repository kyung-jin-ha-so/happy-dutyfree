package com.its.happy.repository;

import com.its.happy.entity.EventFilesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardFilesRepository extends JpaRepository<EventFilesEntity, Long> {
}
