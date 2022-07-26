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
    private String departureDate;
    private String departureNumber;
    private String arrivalRegion;
    private String departureFeature;

    public DepartureDTO(String departureAirport, String departureDate, String departureNumber, String arrivalRegion, String departureFeature) {
        this.departureAirport = departureAirport;
        this.departureDate = departureDate;
        this.departureNumber = departureNumber;
        this.arrivalRegion = arrivalRegion;
        this.departureFeature = departureFeature;
    }

    public static DepartureDTO toDTO(DepartureEntity departureEntity) {
        DepartureDTO departureDTO = new DepartureDTO();
        departureDTO.setDepartureId(departureEntity.getDepartureId());
        departureDTO.setDepartureAirport(departureEntity.getDepartureAirport());
        departureDTO.setDepartureDate(departureEntity.getDepartureDate());
        departureDTO.setDepartureNumber(departureEntity.getDepartureNumber());
        departureDTO.setArrivalRegion(departureEntity.getArrivalRegion());
        departureDTO.setDepartureFeature(departureEntity.getDepartureFeature());
        return departureDTO;
    }
}
