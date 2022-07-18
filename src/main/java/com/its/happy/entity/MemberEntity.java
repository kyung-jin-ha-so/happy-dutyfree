package com.its.happy.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name="member_table")
public class MemberEntity {

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


}
