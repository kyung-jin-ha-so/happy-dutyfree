package com.its.happy.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@Table(name = "board_table")
public class BoardEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long event_id;

    @Column
    private String event_title;

    @Column
    private String event_contents;

    @Column
    private String event_thumbnail;

    // 이벤트게시물(1)이 쿠폰(n)을 참조함
    @OneToMany(mappedBy = "couponEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CouponEntity> couponEntityList = new ArrayList<>();
}
