package com.its.happy.dto;

import com.its.happy.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointDTO {

    private Long pointId;
    private Long memberId;
    private int pointValue;
    private LocalDateTime PointCreatedTime;

}
