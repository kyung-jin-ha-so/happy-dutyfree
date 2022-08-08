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
    // 주문 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long orderId;

    // 주문 상태
    @Column(length = 20)
    private String orderStatus;

    // 최종 결제 달러
    @Column(nullable = false)
    private double orderUsd;

    // 최종 결제 원화
    @Column(nullable = false)
    private double orderWon;

    // 인터넷 회원 할인가
    @Column
    private Long netDcWon;

    // 적립금 사용금액
    @Column
    private Long pointUseValue;

    // 쿠폰 사용금액
    @Column
    private Long couponUseValue;

    // 적용 환율
    @Column(nullable = false)
    private double exchangeRate;

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
