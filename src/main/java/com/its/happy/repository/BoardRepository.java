package com.its.happy.repository;

import com.its.happy.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<EventEntity, Long> {
}
