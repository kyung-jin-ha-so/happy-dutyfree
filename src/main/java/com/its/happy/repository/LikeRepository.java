package com.its.happy.repository;

import com.its.happy.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByProductEntity_ProductIdAndMemberEntity_MemberId(Long productId, Long memberId);
    void deleteByMemberEntity_MemberIdAndProductEntity_ProductId(Long memberId, Long productId);
}
