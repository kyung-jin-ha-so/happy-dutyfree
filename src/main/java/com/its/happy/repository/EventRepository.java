package com.its.happy.repository;

import com.its.happy.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
    List<EventEntity> findByEventTitleContainingOrEventContentsContaining(String q, String q1);
}
