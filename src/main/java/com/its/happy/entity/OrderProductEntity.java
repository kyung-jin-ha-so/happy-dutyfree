package com.its.happy.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "order_product_table")
public class OrderProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long orderProductId;

    @Column(nullable = false)
    private int orderQty;

    @Column(nullable = false)
    private double orderWon;

    @Column(nullable = false)
    private double orderDollar;

    // OrderProductEntity(N)가 OrderEntity(1)을 참조함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;

    // OrderProductEntity(N)가 ProductEntity(1)을 참조함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

}
