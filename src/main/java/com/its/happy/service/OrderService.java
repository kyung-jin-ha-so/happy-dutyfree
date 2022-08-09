package com.its.happy.service;

import com.its.happy.dto.OrderDTO;
import com.its.happy.entity.CouponMemberEntity;
import com.its.happy.entity.MemberEntity;
import com.its.happy.entity.OrderEntity;
import com.its.happy.repository.CouponMemberRepository;
import com.its.happy.repository.MemberRepository;
import com.its.happy.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final CouponMemberRepository couponMemberRepository;

    public Long save(OrderDTO orderDTO) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(orderDTO.getMemberId());
        if (optionalMemberEntity.isPresent()) {
            if (orderDTO.getCouponMemberId() != null) {
                Optional<CouponMemberEntity> optionalCouponMemberEntity = couponMemberRepository.findById(orderDTO.getCouponMemberId());
                if (optionalCouponMemberEntity.isPresent()) {
                    MemberEntity memberEntity = optionalMemberEntity.get();
                    CouponMemberEntity couponMemberEntity = optionalCouponMemberEntity.get();
                    return orderRepository.save(OrderEntity.toEntity(orderDTO, couponMemberEntity, memberEntity)).getOrderId();
                }
            } else {
                MemberEntity memberEntity = optionalMemberEntity.get();
                CouponMemberEntity couponMemberEntity = null;
                return orderRepository.save(OrderEntity.toEntity(orderDTO, couponMemberEntity, memberEntity)).getOrderId();
            }
        }
        return null;
    }

    public List<OrderDTO> findByMemberId(Long memberId) {
        List<OrderEntity> orderEntityList = orderRepository.findByMemberEntityMemberId(memberId);
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (OrderEntity o :
                orderEntityList) {
            if (o.getCouponMemberEntity() != null) {
                orderDTOList.add(OrderDTO.toDTO(o));
            } else {
                orderDTOList.add(OrderDTO.toDTO2(o));
            }
        }
        return orderDTOList;
    }

    public OrderDTO findById(Long orderId) {
        Optional<OrderEntity> optionalOrderEntity = orderRepository.findById(orderId);
        if (optionalOrderEntity.isPresent()) {
            OrderEntity orderEntity = optionalOrderEntity.get();
            if (orderEntity.getCouponMemberEntity() != null) {
                return OrderDTO.toDTO(orderEntity);
            } else {
                return OrderDTO.toDTO2(orderEntity);
            }
        }
        return null;
    }
}
