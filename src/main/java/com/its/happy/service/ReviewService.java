package com.its.happy.service;

import com.its.happy.dto.ReviewDTO;
import com.its.happy.entity.ProductEntity;
import com.its.happy.entity.ReviewEntity;
import com.its.happy.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewEntity findById(Long orderProductId) {
        Optional<ReviewEntity> optionalReviewEntity = reviewRepository.findById(orderProductId);
        if(optionalReviewEntity.isPresent()){
            ReviewEntity reviewEntity = optionalReviewEntity.get();
            return reviewEntity;
        }return null;
    }

//    public List<ReviewDTO> findByMemberId(Long memberId) {
//    }
}
