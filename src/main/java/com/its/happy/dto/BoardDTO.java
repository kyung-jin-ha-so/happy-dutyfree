package com.its.happy.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardDTO {
    private Long eventId;
    private String eventTitle;
    private String eventContents;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String eventThumbnail;
    private Long couponId;
}
