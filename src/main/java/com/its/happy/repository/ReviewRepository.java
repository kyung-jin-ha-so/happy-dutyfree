package com.its.happy.repository;

import com.its.happy.entity.ProductEntity;
import com.its.happy.entity.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findByMemberEntityMemberId(Long memberId);

    @Query(value = "select avg(r.reviewStar) from ReviewEntity r where r.productEntity.productId = :productId")
    Double findAvg(@Param("productId") Long productId);

}
