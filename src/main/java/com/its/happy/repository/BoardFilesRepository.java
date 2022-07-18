package com.its.happy.repository;

import com.its.happy.entity.BoardFilesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardFilesRepository extends JpaRepository<BoardFilesEntity, Long> {
}
