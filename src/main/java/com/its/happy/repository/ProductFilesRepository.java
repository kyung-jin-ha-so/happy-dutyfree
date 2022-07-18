package com.its.happy.repository;

import com.its.happy.entity.ProductFilesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductFilesRepository extends JpaRepository<ProductFilesEntity, Long> {
}
