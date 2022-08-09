package com.its.happy.repository;

import com.its.happy.entity.OrderDepartureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDepartureRepository extends JpaRepository<OrderDepartureEntity, Long> {
}
