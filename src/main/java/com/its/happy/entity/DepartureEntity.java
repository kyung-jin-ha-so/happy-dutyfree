package com.its.happy.entity;

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
    private LocalDateTime departureDatetime;

    @Column(length = 20, nullable = false)
    private String departureNumber;

    // DepartureEntity(N)가 MemberEntity(1)을 참조함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;
}
