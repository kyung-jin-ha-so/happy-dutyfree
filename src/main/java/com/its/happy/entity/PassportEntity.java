package com.its.happy.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "passport_table")
public class PassportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long passportId;

    @Column(length = 30, nullable = false, unique = true)
    private String passportNumber;

    @Column(length = 30, nullable = false)
    private String passportName;

    @Column(length = 30, nullable = false)
    private String passportDate;

    // PassportEntity(1)가 MemberEntity(1)을 참조함
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;
}
