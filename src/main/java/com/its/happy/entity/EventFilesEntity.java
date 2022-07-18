package com.its.happy.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "eventFiles_table")
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
}
