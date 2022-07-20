package com.its.happy.entity;

import com.its.happy.dto.EventFilesDTO;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "event_files_table")
public class EventFilesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_file_id")
    private Long eventFileId;

    @Column(length = 50, nullable = false)
    private String eventFileName;

    // EventFile(n)이 Event(1)를 참조함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private EventEntity eventEntity;

    public static EventFilesEntity toEventFile(EventFilesDTO eventFilesDTO, EventEntity eventEntity) {
        EventFilesEntity eventFilesEntity = new EventFilesEntity();
        eventFilesEntity.setEventFileName(eventFilesDTO.getEventFileName());
        eventFilesEntity.setEventEntity(eventEntity);
        return eventFilesEntity;
    }

}
