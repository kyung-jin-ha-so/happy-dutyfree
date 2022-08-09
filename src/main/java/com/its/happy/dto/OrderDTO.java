package com.its.happy.dto;

import com.its.happy.entity.OrderDepartureEntity;
import com.its.happy.entity.OrderEntity;
import com.its.happy.entity.OrderProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;


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
    private List<OrderProductEntity> orderProductEntityList;
    private OrderDepartureEntity orderDepartureEntity;

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
        orderDTO.setMemberId(orderEntity.getMemberEntity().getMemberId());
        orderDTO.setOrderProductEntityList(orderEntity.getOrderProductEntityList());
        orderDTO.setOrderDepartureEntity(orderEntity.getOrderDepartureEntity());
        return orderDTO;
    }

    // CouponMemberEntity가 null일 경우
    public static OrderDTO toDTO2(OrderEntity orderEntity) {
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
        orderDTO.setMemberId(orderEntity.getMemberEntity().getMemberId());
        orderDTO.setOrderProductEntityList(orderEntity.getOrderProductEntityList());
        orderDTO.setOrderDepartureEntity(orderEntity.getOrderDepartureEntity());
        return orderDTO;
    }
}
