package com.its.happy.dto;

import com.its.happy.entity.MemberEntity;
import com.its.happy.entity.OrderEntity;
import com.its.happy.entity.ProductEntity;
import com.its.happy.entity.ReviewEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Long reviewId;
    private String reviewContents;
    private double reviewStar;
    private LocalDateTime reviewCreatedTime;
    private LocalDateTime reviewUpdatedTime;
    private MemberEntity memberEntity;
    private ProductEntity productEntity;
    private OrderEntity orderEntity;

    public static ReviewDTO toDTO(ReviewEntity reviewEntity) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setReviewId(reviewEntity.getReviewId());
        reviewDTO.setReviewContents(reviewEntity.getReviewContents());
        reviewDTO.setReviewStar(reviewEntity.getReviewStar());
        reviewDTO.setReviewCreatedTime(reviewEntity.getCreatedTime());
        reviewDTO.setMemberEntity(reviewEntity.getMemberEntity());
        reviewDTO.setProductEntity(reviewEntity.getProductEntity());
        reviewDTO.setOrderEntity(reviewEntity.getOrderEntity());
        return reviewDTO;
    }
}
