package com.its.happy.dto;

import com.its.happy.entity.MemberEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SearchDTO {

    private Long searchId;
    private String searchName;
    private LocalDateTime searchCreatedTime;
    private MemberEntity memberEntity;


}
