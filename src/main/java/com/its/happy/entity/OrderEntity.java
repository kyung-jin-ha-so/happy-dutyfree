package com.its.happy.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "order_table")
public class OrderEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long orderId;

    @Column(length = 20)
    private String orderStatus;

    // OrderEntity(N)가 MemberEntity(1)을 참조함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    // OrderEntity(1)가 ReviewEntity(N)한테 참조당함
    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<ReviewEntity> reviewEntityList = new ArrayList<>();

    // OrderEntity(1)가 OrderProductEntity(N)한테 참조당함
    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<OrderProductEntity> orderProductEntityList = new ArrayList<>();

    // OrderEntity(1)가 OrderDepartureEntity(1)한테 참조당함
    @OneToOne(mappedBy = "orderEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private OrderDepartureEntity orderDepartureEntity;

    @PreRemove
    private void preRemove() {
        reviewEntityList.forEach(review -> review.setOrderEntity(null));
        orderProductEntityList.forEach(orderProduct -> orderProduct.setOrderEntity(null));
    }
}
