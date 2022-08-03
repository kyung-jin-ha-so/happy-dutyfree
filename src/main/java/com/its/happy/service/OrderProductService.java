package com.its.happy.service;

import com.its.happy.entity.OrderProductEntity;
import com.its.happy.repository.OrderProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderProductService {
    private final OrderProductRepository orderProductRepository;

    public OrderProductEntity findById(Long orderProductId) {
        Optional<OrderProductEntity> optionalOrderProductEntity = orderProductRepository.findById(orderProductId);
        if(optionalOrderProductEntity.isPresent()){
            OrderProductEntity orderProductEntity = optionalOrderProductEntity.get();
            return orderProductEntity;
        } return null;
    }
}
