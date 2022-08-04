package com.its.happy.repository;

import com.its.happy.entity.PointEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointRepository extends JpaRepository<PointEntity, Long> {

    List<PointEntity> findByMemberEntity_MemberId(Long memberId);

}
