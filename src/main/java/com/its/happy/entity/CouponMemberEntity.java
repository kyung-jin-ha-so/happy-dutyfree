package com.its.happy.entity;

import com.its.happy.dto.CouponDTO;
import com.its.happy.dto.CouponMemberDTO;
import com.its.happy.dto.EventDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "coupon_member_table")
public class CouponMemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long couponMemberId;

    @Column(length = 20)
    private String couponStatus;


    // CouponMemberEntity(N)가 CouponEntity(1)을 참조함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private CouponEntity couponEntity;

    // CouponMemberEntity(N)가 MemberEntity(1)을 참조함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    public static CouponMemberEntity toCouponMember(MemberEntity memberEntity, CouponEntity couponEntity) {
        CouponMemberEntity couponMemberEntity = new CouponMemberEntity();
        couponMemberEntity.setCouponStatus("사용 전");
        couponMemberEntity.setCouponEntity(couponEntity);
        couponMemberEntity.setMemberEntity(memberEntity);
        return couponMemberEntity;
    }
}
