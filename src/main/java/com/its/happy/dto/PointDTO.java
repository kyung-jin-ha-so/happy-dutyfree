package com.its.happy.dto;

import com.its.happy.entity.MemberEntity;
import com.its.happy.entity.PointEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointDTO {

    private Long pointId;
    private Long memberId;
    private int pointValue;
    private LocalDateTime PointCreatedTime;

    public static PointDTO toPointDTO(PointEntity pointEntity) {
        PointDTO pointDTO = new PointDTO();
        pointDTO.setPointId(pointEntity.getPointId());
        pointDTO.setPointValue(pointEntity.getPointValue());
        pointDTO.setPointCreatedTime(pointEntity.getCreatedTime());
        return pointDTO;
    }
}
