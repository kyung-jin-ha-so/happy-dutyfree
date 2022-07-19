package com.its.happy.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "coupon_table")
public class CouponEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long couponId;

    @Column(length = 30, nullable = false)
    private String couponName;

    @Column(nullable = false)
    private long couponValue;

    @Column(nullable = false)
    private long couponMinimumValue;

    @Column(length = 50, nullable = false)
    private String couponThumbnail;

    // CouponEntity(1)가 BoardEntity(N)한테 참조당함
    @OneToMany(mappedBy = "couponEntity", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<EventEntity> eventEntityList = new ArrayList<>();

    // CouponEntity(1)가 CouponMemberEntity(N)한테 참조당함
    @OneToMany(mappedBy = "couponEntity", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<CouponMemberEntity> couponMemberEntityList = new ArrayList<>();

    @PreRemove
    private void preRemove() {
        eventEntityList.forEach(event -> event.setCouponEntity(null));
        couponMemberEntityList.forEach(couponMember -> couponMember.setCouponEntity(null));
    }
}
