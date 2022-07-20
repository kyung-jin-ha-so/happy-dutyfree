package com.its.happy.repository;

import com.its.happy.entity.MemberEntity;
import com.its.happy.entity.PassportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassportRepository extends JpaRepository<PassportEntity, Long> {
    Optional<PassportEntity> findByMemberEntity(MemberEntity memberEntity);
}
