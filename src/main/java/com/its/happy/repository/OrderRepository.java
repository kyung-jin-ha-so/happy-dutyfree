package com.its.happy.repository;

import com.its.happy.entity.CouponMemberEntity;
import com.its.happy.entity.MemberEntity;
import com.its.happy.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByMemberEntityMemberId(Long memberId);

//    오늘의 주문 명단 뽑기
    List<OrderEntity> findAllByCreatedTimeBetween(LocalDateTime start, LocalDateTime end);
}
