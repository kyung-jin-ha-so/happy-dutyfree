package com.its.happy.repository;

import com.its.happy.entity.ProductEntity;
import com.its.happy.entity.ProductFilesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Page<ProductEntity> findByCategoryEntityCategoryId(PageRequest pageRequest, Long categoryId);

    Page<ProductEntity> findByProductNameContaining(PageRequest pageRequest, String q);

    List<ProductEntity> findByProductDiscountGreaterThanEqual(Long discount);
}
