package com.its.happy.repository;

import com.its.happy.entity.CouponMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CouponMemberRepository extends JpaRepository<CouponMemberEntity, Long> {
    List<CouponMemberEntity> findByCouponMemberId(Long couponId);

//    Optional<CouponMemberEntity>findByCouponEntity_CouponNameAndMemberEntity_MemberId(String couponName, Long memberId);
    Optional<CouponMemberEntity>findByCouponEntity_CouponNameAndMemberEntity_MemberIdAndCouponEntity_CouponId(String couponName, Long memberId, Long couponId);
}
