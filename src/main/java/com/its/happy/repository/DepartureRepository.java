package com.its.happy.repository;

import com.its.happy.entity.DepartureEntity;
import com.its.happy.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartureRepository extends JpaRepository<DepartureEntity, Long> {
    List<DepartureEntity> findAllByMemberEntity(MemberEntity memberEntity);
}
