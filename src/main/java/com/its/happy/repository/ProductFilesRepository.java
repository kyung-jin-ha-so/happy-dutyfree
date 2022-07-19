package com.its.happy.repository;

import com.its.happy.entity.ProductFilesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductFilesRepository extends JpaRepository<ProductFilesEntity, Long> {
    List<ProductFilesEntity> findByProductEntityProductId(Long productId);

}
