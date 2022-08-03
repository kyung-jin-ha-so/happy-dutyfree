package com.its.happy.service;

import com.its.happy.dto.ReviewDTO;
import com.its.happy.entity.MemberEntity;
import com.its.happy.entity.OrderEntity;
import com.its.happy.entity.ProductEntity;
import com.its.happy.entity.ReviewEntity;
import com.its.happy.repository.MemberRepository;
import com.its.happy.repository.OrderRepository;
import com.its.happy.repository.ProductRepository;
import com.its.happy.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public ReviewDTO findById(Long reviewId) {
        Optional<ReviewEntity> optionalReviewEntity = reviewRepository.findById(reviewId);
        if (optionalReviewEntity.isPresent()) {
            ReviewEntity reviewEntity = optionalReviewEntity.get();
            return ReviewDTO.toDTO(reviewEntity);
        }
        return null;
    }

    public List<ReviewDTO> findByMemberId(Long memberId) {
        List<ReviewEntity> reviewEntityList = reviewRepository.findByMemberEntityMemberId(memberId);
        List<ReviewDTO> reviewDTOList = new ArrayList<>();
        for (ReviewEntity review : reviewEntityList) {
            reviewDTOList.add(ReviewDTO.toDTO(review));
        }
        return reviewDTOList;
    }

    public void deleteById(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    public void save(ReviewDTO reviewDTO, Long memberId, Long productId, Long orderId) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(productId);
        Optional<OrderEntity> optionalOrderEntity = orderRepository.findById(orderId);
        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            if (optionalProductEntity.isPresent()) {
                ProductEntity productEntity = optionalProductEntity.get();
                if (optionalOrderEntity.isPresent()) {
                    OrderEntity orderEntity = optionalOrderEntity.get();
                    ReviewEntity.toSaveEntity(reviewDTO, memberEntity, productEntity, orderEntity);
                    reviewRepository.save(ReviewEntity.toSaveEntity(reviewDTO, memberEntity, productEntity, orderEntity));
                }
            }
        }
    }
}
