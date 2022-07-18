package com.its.happy.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardDTO {
    private Long event_id;
    private String event_title;
    private String event_contents;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String event_thumbnail;
}
