package com.its.happy.dto;

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
    private Long pointUseValue;
    private Long couponUseValue;
    private double exchangeRate;
}
