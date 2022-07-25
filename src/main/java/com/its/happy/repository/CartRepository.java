package com.its.happy.repository;

import com.its.happy.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
    @Modifying
    @Query(value = "update CartEntity  c set c.cartQty = c.cartQty+1 where c.productEntity.productId= :productId and c.memberEntity.memberId= :memberId")
    void cartQty(@Param("productId") Long productId, @Param("memberId") Long memberId);

    Optional<CartEntity> findByProductEntity_ProductIdAndMemberEntity_MemberId(Long productId, Long memberId);
}
