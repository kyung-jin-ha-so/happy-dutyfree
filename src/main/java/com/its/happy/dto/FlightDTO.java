package com.its.happy.dto;

import com.its.happy.entity.DepartureEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightDTO {
    private String departureDate;
    private String cityCode;
    private String airline;
    private String airport;
    private String airportCode;
    private String internationalNum;
    private String internationalTime;
    private String airlineKorean;

    public FlightDTO(String departureDate, String cityCode, String airline) {
        this.departureDate = departureDate;
        this.cityCode = cityCode;
        this.airline = airline;
    }
}
