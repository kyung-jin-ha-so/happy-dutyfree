package com.its.happy.entity;


import com.its.happy.dto.PointDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "point_table")
public class PointEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private Long pointId;

    @Column(name = "point_value", nullable = false)
    private String pointValue;

    // 적립금(n)이 회원(1)을 참조함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    public static PointEntity toSave(MemberEntity memberEntity){
        PointEntity pointEntity = new PointEntity();
        pointEntity.setPointValue("1000000");
        pointEntity.setMemberEntity(memberEntity);
        return pointEntity;
    }
}


