package com.its.happy.entity;

import com.its.happy.dto.PassportDTO;
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

    public static PassportEntity toEntity(PassportDTO passportDTO, MemberEntity memberEntity) {
        PassportEntity passportEntity = new PassportEntity();
        passportEntity.setPassportId(passportDTO.getPassportId());
        passportEntity.setPassportNumber(passportDTO.getPassportNumber());
        passportEntity.setPassportName(passportDTO.getPassportName());
        passportEntity.setPassportDate(passportDTO.getPassportDate());
        passportEntity.setMemberEntity(memberEntity);
        return passportEntity;
    }
}
