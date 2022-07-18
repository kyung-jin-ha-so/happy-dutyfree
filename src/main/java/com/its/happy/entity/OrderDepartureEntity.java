package com.its.happy.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "order_departure_table")
public class OrderDepartureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long orderDepartureId;

    @Column(length = 20, nullable = false)
    private String orderDepartureAirport;

    @Column(nullable = false)
    private LocalDateTime orderDepartureDatetime;

    @Column(length = 20, nullable = false)
    private String orderDepartureNumber;

    // OrderDepartureEntity(N)가 MemberEntity(1)을 참조함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    // OrderDepartureEntity(1)가 OrderEntity(1)을 참조함
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;

}
