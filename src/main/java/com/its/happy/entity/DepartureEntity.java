package com.its.happy.entity;

import com.its.happy.dto.DepartureDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "departure_table")
public class DepartureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long departureId;

    @Column(length = 20, nullable = false)
    private String departureAirport;

    @Column(length = 20, nullable = false)
    private String departureDate;

    @Column(length = 20, nullable = false)
    private String departureNumber;

    @Column(length = 30, nullable = false)
    private String arrivalRegion;

    @Column(length = 10, nullable = false)
    private String departureFeature;

    // DepartureEntity(N)가 MemberEntity(1)을 참조함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    public static DepartureEntity toEntity(DepartureDTO departureDTO, MemberEntity memberEntity) {
        DepartureEntity departureEntity = new DepartureEntity();
        departureEntity.setDepartureId(departureDTO.getDepartureId());
        departureEntity.setDepartureAirport(departureDTO.getDepartureAirport());
        departureEntity.setDepartureDate(departureDTO.getDepartureDate());
        departureEntity.setDepartureNumber(departureDTO.getDepartureNumber());
        departureEntity.setArrivalRegion(departureDTO.getArrivalRegion());
        departureEntity.setDepartureFeature(departureDTO.getDepartureFeature());
        departureEntity.setMemberEntity(memberEntity);
        return departureEntity;
    }
}
