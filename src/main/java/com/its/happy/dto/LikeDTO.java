package com.its.happy.dto;

import com.its.happy.entity.LikeEntity;
import com.its.happy.entity.MemberEntity;
import com.its.happy.entity.ProductEntity;
import lombok.Data;

@Data
public class LikeDTO {
    private Long likeId;
    private ProductEntity productEntity;
    private MemberEntity memberEntity;

    public static LikeDTO toLikeDTO(LikeEntity likeEntity) {
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setLikeId(likeEntity.getLikeId());
        likeDTO.setProductEntity(likeEntity.getProductEntity());
        likeDTO.setMemberEntity(likeEntity.getMemberEntity());
        return likeDTO;
    }
}
