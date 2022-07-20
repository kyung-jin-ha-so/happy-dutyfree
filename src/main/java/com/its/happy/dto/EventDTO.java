package com.its.happy.dto;

import com.its.happy.entity.CouponEntity;
import com.its.happy.entity.EventEntity;
import com.its.happy.entity.EventFilesEntity;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventDTO {
    private Long eventId;
    private String eventTitle;
    private String eventContents;
    private LocalDateTime eventCreatedDate;
    private LocalDateTime eventUpdatedDate;
    private String eventThumbnailName;
    private MultipartFile eventThumbnail;
    private CouponEntity couponEntity;
    private EventFilesEntity eventFilesEntity;
    private List<EventFilesEntity> eventFilesEntityList;

    public EventDTO(Long eventId, String eventTitle, String eventContents, LocalDateTime eventCreatedDate, LocalDateTime eventUpdatedDate, String eventThumbnailName, CouponEntity couponEntity) {
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.eventContents = eventContents;
        this.eventCreatedDate = eventCreatedDate;
        this.eventUpdatedDate = eventUpdatedDate;
        this.eventThumbnailName = eventThumbnailName;
        this.couponEntity = couponEntity;
    }

    public EventDTO() {

    }
    public static EventDTO toEventDTO(EventEntity eventEntity) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setEventId(eventEntity.getEventId());
        eventDTO.setEventTitle(eventEntity.getEventTitle());
        eventDTO.setEventContents(eventEntity.getEventContents());
        eventDTO.setEventCreatedDate(eventEntity.getCreatedTime());
        eventDTO.setEventUpdatedDate(eventEntity.getUpdatedTime());
        eventDTO.setEventThumbnailName(eventEntity.getEventThumbnail());
        eventDTO.setCouponEntity(eventEntity.getCouponEntity());
        eventDTO.setEventFilesEntityList(eventEntity.getEventFilesEntityList());
        return eventDTO;
    }
}
