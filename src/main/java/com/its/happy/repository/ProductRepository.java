package com.its.happy.repository;

import com.its.happy.entity.ProductEntity;
import com.its.happy.entity.ProductFilesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    //판매중 또는 품절 상태인 상품 목록 (카테고리별)
    Page<ProductEntity> findByCategoryEntityCategoryIdAndProductStatusNotContaining(PageRequest pageRequest, Long categoryId, String status);

    //검색한 결과
    Page<ProductEntity> findByProductNameContaining(PageRequest pageRequest, String q);

    //검색한 결과(판매중 또는 품절된 상품)
    Page<ProductEntity> findByProductNameContainingAndProductStatusIsNotContaining(PageRequest pageRequest, String q, String status);

    //오늘의 상품 출력(판매중 또는 품절된 상품)
    List<ProductEntity> findByProductDiscountGreaterThanEqualAndProductStatusNotContaining(Long discount, String status);

    //상품 목록 숫자(판매중 또는 품절된 상품)
    long countByCategoryEntityCategoryIdAndProductStatusIsNotContaining(Long categoryId, String status);

    //검색한 상품 목록 숫자(판매중 또는 품절된 상품)
    long countAllByProductNameContainingAndProductStatusIsNotContaining(String q, String status);
}
