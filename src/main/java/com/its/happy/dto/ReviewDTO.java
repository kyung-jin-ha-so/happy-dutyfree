package com.its.happy.dto;

import com.its.happy.entity.MemberEntity;
import com.its.happy.entity.OrderEntity;
import com.its.happy.entity.ProductEntity;
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
}
