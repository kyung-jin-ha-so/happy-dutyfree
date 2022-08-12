package com.its.happy.repository;

import com.its.happy.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByMemberEmail(String memberEmail);
    Optional<MemberEntity> findByMemberMobile(String memberMobile);
    Optional<MemberEntity> findByMemberKakaoId(String memberKakaoId);


}
