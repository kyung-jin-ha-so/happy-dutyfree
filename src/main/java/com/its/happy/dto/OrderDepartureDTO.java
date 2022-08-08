package com.its.happy.dto;

import com.its.happy.entity.MemberEntity;
import com.its.happy.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDepartureDTO {
    private Long orderDepartureId;
    private String orderDepartureAirport;
    private String orderDepartureDate;
    private String orderDepartureNumber;
    private Long memberId;
    private Long orderId;
}
