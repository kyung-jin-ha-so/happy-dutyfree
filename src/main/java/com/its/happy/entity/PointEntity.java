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
    private int pointValue;

    // 적립금(n)이 회원(1)을 참조함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    public static PointEntity toSave(MemberEntity memberEntity,PointDTO pointDTO){
        PointEntity pointEntity = new PointEntity();
        pointEntity.setPointValue(pointDTO.getPointValue());
        pointEntity.setMemberEntity(memberEntity);
        return pointEntity;
    }


}


