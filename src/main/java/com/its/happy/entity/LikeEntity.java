package com.its.happy.entity;

import com.its.happy.dto.LikeDTO;
import com.its.happy.dto.ProductDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "like_table")
public class LikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long likeId;

    // 찜(n)이 상품(1)을 참조함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    // 찜(n)이 회원(1)을 참조함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    public static LikeEntity toLike(ProductEntity productEntity, MemberEntity memberEntity) {
        LikeEntity likeEntity = new LikeEntity();
        likeEntity.setProductEntity(productEntity);
        likeEntity.setMemberEntity(memberEntity);
        return likeEntity;
    }
}
