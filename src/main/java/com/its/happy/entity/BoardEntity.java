package com.its.happy.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@Table(name = "board_table")
public class BoardEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id", unique = true)
    private Long eventId;

    @Column(nullable = false)
    private String eventTitle;

    @Column
    private String eventContents;

    @Column
    private String eventThumbnail;

    // 이벤트게시물(n)이 쿠폰(1)을 참조함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private CouponEntity couponEntity;
}
