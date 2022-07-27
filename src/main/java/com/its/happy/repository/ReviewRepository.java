package com.its.happy.repository;

import com.its.happy.entity.ProductEntity;
import com.its.happy.entity.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findByMemberEntityMemberId(Long memberId);

}
