package com.its.happy.dto;

import com.its.happy.entity.DepartureEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartureDTO {
    private Long departureId;
    private String departureAirport;
    private String departureDatetime;
    private String departureNumber;

    public DepartureDTO(String departureAirport, String departureDatetime, String departureNumber) {
        this.departureAirport = departureAirport;
        this.departureDatetime = departureDatetime;
        this.departureNumber = departureNumber;
    }

    public static DepartureDTO toDTO(DepartureEntity departureEntity) {
        DepartureDTO departureDTO = new DepartureDTO();
        departureDTO.setDepartureId(departureEntity.getDepartureId());
        departureDTO.setDepartureAirport(departureEntity.getDepartureAirport());
        departureDTO.setDepartureDatetime(departureEntity.getDepartureDatetime());
        departureDTO.setDepartureNumber(departureEntity.getDepartureNumber());
        return departureDTO;
    }
}
