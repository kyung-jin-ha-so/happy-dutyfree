package com.its.happy.service;

import com.its.happy.dto.MemberDTO;
import com.its.happy.dto.OrderDTO;
import com.its.happy.entity.CouponMemberEntity;
import com.its.happy.entity.MemberEntity;
import com.its.happy.entity.OrderEntity;
import com.its.happy.repository.CouponMemberRepository;
import com.its.happy.repository.MemberRepository;
import com.its.happy.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final CouponMemberRepository couponMemberRepository;


    public MemberDTO findByMemberId(Long memberId) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            return MemberDTO.toMemberDTO(memberEntity);
        }
        return null;
    }

    public Long save(OrderDTO orderDTO) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(orderDTO.getMemberId());
        Optional<CouponMemberEntity> optionalCouponMemberEntity = couponMemberRepository.findById(orderDTO.getCouponMemberId());
        if (optionalMemberEntity.isPresent()) {
            if (optionalCouponMemberEntity.isPresent()) {
                MemberEntity memberEntity = optionalMemberEntity.get();
                CouponMemberEntity couponMemberEntity = optionalCouponMemberEntity.get();
                return orderRepository.save(OrderEntity.toEntity(orderDTO, couponMemberEntity, memberEntity)).getOrderId();
            }
        }
        return null;
    }
}
