package com.its.happy.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name="member_table")
public class MemberEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_email",length = 30,unique = true,nullable = false)
    private String memberEmail;

    @Column(name = "member_password",length = 30,nullable = false)
    private String memberPassword;

    @Column(name = "member_name",length = 20,nullable = false)
    private String memberName;

    @Column(name = "member_mobile",length = 20,nullable = false)
    private String memberMobile;

    @Column(name = "member_birth",length = 20)
    private String memberBirth;

    @Column(name = "member_kakao_id",length = 20)
    private String memberKakaoId;

    @Column(name = "member_tier",length = 20,nullable = false)
    @ColumnDefault("브론즈")
    private String memberTier;

    // 회원(1)이 적립금(n)한테 참조당함
    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PointEntity> pointEntityList = new ArrayList<>();

    // 회원(1)이 여권정보(1)한테 참조당함
    @OneToOne(mappedBy = "memberEntity")
    private PassportEntity passportEntity;

    // 회원(1)이 후기(n)한테 참조당함
    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ReviewEntity> reviewEntityList = new ArrayList<>();

    // 회원(1)이 검색(n)한테 참조당함
    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SearchEntity> searchEntityList = new ArrayList<>();

    // 회원(1)이 찜(n)한테 참조당함
    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LikeEntity> likeEntityList = new ArrayList<>();

    // 회원(1)이 장바구니(n)한테 참조당함
    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CartEntity> cartEntityList = new ArrayList<>();

    // 회원(1)이 주문(n)한테 참조당함
    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderEntity> orderEntityList = new ArrayList<>();

    // 회원(1)이 주문출국정보(n)한테 참조당함 orderDeparture
    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderDepartureEntity> orderDepartureEntityList = new ArrayList<>();

    // 회원(1)이 쿠폰회원연결테이블(n)한테 참조당함 coupon_member
    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CouponMemberEntity> couponMemberEntityList = new ArrayList<>();

    //회원(1)이 출국정보(n)한테 참조당함 departure
    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DepartureEntity> departureEntityList = new ArrayList<>();
}
