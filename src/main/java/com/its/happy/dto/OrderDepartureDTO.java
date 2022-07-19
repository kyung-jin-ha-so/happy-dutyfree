package com.its.happy.dto;

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
    private LocalDateTime orderDepartureDatetime;
    private String orderDepartureNumber;
}