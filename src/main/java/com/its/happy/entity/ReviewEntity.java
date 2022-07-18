package com.its.happy.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "review_table")
public class ReviewEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;
    @Column(name = "review_contents", nullable = false, length = 300)
    private String reviewContents;
    @Column(name = "review_star", nullable = false)
    private double reviewStar;

    // 상품후기(n)가 회원(1)을 참조함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    // 상품후기(n)가 상품(1)을 참조함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    // 상품후기(n)가 주문(1)을 참조함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;


}
