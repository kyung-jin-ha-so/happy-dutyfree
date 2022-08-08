package com.its.happy.repository;

import com.its.happy.entity.CouponMemberEntity;
import com.its.happy.entity.MemberEntity;
import com.its.happy.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByMemberEntity_MemberId(Long memberId);
}
