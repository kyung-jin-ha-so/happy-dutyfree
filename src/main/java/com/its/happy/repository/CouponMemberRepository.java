package com.its.happy.repository;

import com.its.happy.entity.CouponMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponMemberRepository extends JpaRepository<CouponMemberEntity, Long> {
    List<CouponMemberEntity> findByCouponMemberId(Long couponId);
}
