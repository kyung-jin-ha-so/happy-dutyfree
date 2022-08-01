package com.its.happy.dto;

import com.its.happy.entity.MemberEntity;
import com.its.happy.entity.SearchEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SearchDTO {

    private Long searchId;
    private String searchName;
    private LocalDateTime searchCreatedTime;
    private MemberEntity memberEntity;


    public static SearchDTO toDTO(SearchEntity searchEntity) {
        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setSearchId(searchEntity.getSearchId());
        searchDTO.setSearchName(searchEntity.getSearchName());
        searchDTO.setSearchCreatedTime(searchEntity.getCreatedTime());
        searchDTO.setMemberEntity(searchEntity.getMemberEntity());
        return searchDTO;
    }
}
