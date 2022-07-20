package com.its.happy.entity;

import com.its.happy.dto.EventDTO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "event_table")
@NoArgsConstructor(access = AccessLevel.PUBLIC)

public class EventEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    @Column(nullable = false, length = 100)
    private String eventTitle;

    @Column(nullable = false, length = 500)
    private String eventContents;

    @Column
    private String eventThumbnail;

    // 이벤트게시물(n)이 쿠폰(1)을 참조함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private CouponEntity couponEntity;

    // 이벤트게시물(1)이 이벤트 파일(n)에게 참조당함
    @OneToMany(mappedBy = "eventEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<EventFilesEntity> eventFilesEntityList = new ArrayList<>();


    public static EventEntity toEvent(EventDTO eventDTO, CouponEntity couponEntity) {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setEventTitle(eventDTO.getEventTitle());
        eventEntity.setEventContents(eventDTO.getEventContents());
        eventEntity.setEventThumbnail(eventDTO.getEventThumbnailName());
        eventEntity.setCouponEntity(couponEntity);
        eventEntity.setEventFilesEntityList(eventDTO.getEventFilesEntityList());
        return eventEntity;
    }

    public static EventEntity toUpdateEntity(EventDTO eventDTO) {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setEventId(eventDTO.getEventId());
        eventEntity.setEventTitle(eventDTO.getEventTitle());
        eventEntity.setEventContents(eventDTO.getEventContents());
        eventEntity.setEventContents(eventDTO.getEventContents());
        eventEntity.setEventThumbnail(eventDTO.getEventThumbnailName());
        return eventEntity;
    }
}
