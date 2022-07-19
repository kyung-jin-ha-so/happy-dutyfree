package com.its.happy.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "payment_table")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long paymentId;

    @Column(nullable = false)
    private double paymentWon;

    @Column(nullable = false)
    private double paymentDollar;

    @Column
    private Long pointUseValue;

    // PaymentEntity(1)가 OrderEntity(1)을 참조함
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;
}
