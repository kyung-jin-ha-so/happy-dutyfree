package com.its.happy.repository;

import com.its.happy.entity.SearchEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchRepository extends JpaRepository<SearchEntity, Long> {

    List<SearchEntity> findTop10ByMemberEntityMemberId(Long loginId, Sort sort);

}
