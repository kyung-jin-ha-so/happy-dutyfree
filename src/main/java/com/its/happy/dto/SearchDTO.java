package com.its.happy.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SearchDTO {

    private Long searchId;
    private String searchName;
    private LocalDateTime searchCreatedTime;

}
