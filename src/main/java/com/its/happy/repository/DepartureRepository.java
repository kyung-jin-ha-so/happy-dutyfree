package com.its.happy.repository;

import com.its.happy.entity.DepartureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartureRepository extends JpaRepository<DepartureEntity, Long> {
}
