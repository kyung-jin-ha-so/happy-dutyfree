package com.its.happy.repository;

import com.its.happy.entity.SearchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchRepository extends JpaRepository<SearchEntity, Long> {
}
