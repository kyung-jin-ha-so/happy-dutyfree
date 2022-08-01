package com.its.happy.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "search_table")
public class SearchEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "search_id")
    private Long searchId;

    @Column(name = "search_name", length = 30, nullable = false)
    private String searchName;

    // 검색(n)이 회원(1)을 참조함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;


    public static SearchEntity toSaveEntity(String q, MemberEntity memberEntity) {
        SearchEntity searchEntity = new SearchEntity();
        searchEntity.setSearchName(q);
        searchEntity.setMemberEntity(memberEntity);
        return searchEntity;
    }

    public static SearchEntity toSaveWithOutMember(String q) {
        SearchEntity searchEntity = new SearchEntity();
        searchEntity.setSearchName(q);
        return searchEntity;
    }
}
