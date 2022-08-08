package com.its.happy.dto;

import com.its.happy.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long orderId;
    private LocalDateTime orderCreatedTime;
    private LocalDateTime orderUpdatedTime;
    private String orderStatus;
    private Long netDcWon;
    private double orderUsd;
    private double orderWon;
    private int pointUseValue;
    private Long couponUseValue;
    private double exchangeRate;
    private Long couponMemberId;
    private Long memberId;

    public static OrderDTO toDTO(OrderEntity orderEntity) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(orderEntity.getOrderId());
        orderDTO.setOrderCreatedTime(orderEntity.getCreatedTime());
        orderDTO.setOrderUpdatedTime(orderEntity.getUpdatedTime());
        orderDTO.setOrderStatus(orderEntity.getOrderStatus());
        orderDTO.setNetDcWon(orderEntity.getNetDcWon());
        orderDTO.setOrderUsd(orderEntity.getOrderUsd());
        orderDTO.setOrderWon(orderEntity.getOrderWon());
        orderDTO.setPointUseValue(orderEntity.getPointUseValue());
        orderDTO.setCouponUseValue(orderEntity.getCouponUseValue());
        orderDTO.setExchangeRate(orderEntity.getExchangeRate());
        orderDTO.setCouponMemberId(orderEntity.getCouponMemberEntity().getCouponMemberId());
        orderDTO.setMemberId(orderEntity.getCouponMemberEntity().getMemberEntity().getMemberId());
        return orderDTO;
    }
}
