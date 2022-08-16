package com.its.happy.service;

import com.its.happy.dto.MemberDTO;
import com.its.happy.dto.SearchDTO;
import com.its.happy.entity.SearchEntity;
import com.its.happy.repository.MemberRepository;
import com.its.happy.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchRepository searchRepository;

    //회원별 최근 검색어 찾기
    public List<SearchDTO> findByMemberId(Long loginId) {
        List<SearchEntity> searchEntityList = searchRepository.findTop10ByMemberEntityMemberId(loginId, Sort.by(Sort.Direction.DESC, "createdTime"));
        List<SearchDTO> searchDTOList = new ArrayList<>();
        for (SearchEntity searchEntity: searchEntityList) {
            searchDTOList.add(SearchDTO.toDTO(searchEntity));
        }return searchDTOList;
    }

    // 최근 검색어 삭제
    public void deleteById(Long searchId) {
        searchRepository.deleteById(searchId);
    }


}
