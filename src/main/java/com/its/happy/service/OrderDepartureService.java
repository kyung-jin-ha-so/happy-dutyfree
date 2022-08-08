package com.its.happy.service;

import com.its.happy.dto.OrderDepartureDTO;
import com.its.happy.entity.*;
import com.its.happy.repository.CouponMemberRepository;
import com.its.happy.repository.MemberRepository;
import com.its.happy.repository.OrderDepartureRepository;
import com.its.happy.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OrderDepartureService {
    private final OrderDepartureRepository orderDepartureRepository;
    private final MemberRepository memberRepository;
    private final CouponMemberRepository couponMemberRepository;
    private final OrderRepository orderRepository;


    public void save(OrderDepartureDTO orderDepartureDTO) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(orderDepartureDTO.getMemberId());
        Optional<OrderEntity> optionalOrderEntity = orderRepository.findById(orderDepartureDTO.getOrderId());
        if (optionalMemberEntity.isPresent()) {
            if (optionalOrderEntity.isPresent()) {
                OrderEntity orderEntity = optionalOrderEntity.get();
                MemberEntity memberEntity = optionalMemberEntity.get();
                orderDepartureRepository.save(OrderDepartureEntity.toEntity(orderDepartureDTO, memberEntity, orderEntity));
            }
        }
    }
}
