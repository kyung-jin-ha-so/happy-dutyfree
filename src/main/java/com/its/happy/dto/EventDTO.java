package com.its.happy.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class EventDTO {
    private Long eventId;
    private String eventTitle;
    private String eventContents;
    private LocalDateTime eventCreatedDate;
    private LocalDateTime eventUpdatedDate;
    private String eventThumbnailName;
    private MultipartFile eventThumbnail;
}
