package com.its.happy.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "boardFiles_table")
public class BoardFilesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_file_id", unique = true)
    private Long eventFileId;

    @Column
    private String eventFileName;

    // BoardFile(n)이 Board(1)를 참조함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private BoardEntity boardEntity;
}
