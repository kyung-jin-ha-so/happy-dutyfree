package com.its.happy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartureDTO {
    private Long departureId;
    private String departureAirport;
    private LocalDateTime departureDatetime;
    private String departureNumber;
}
