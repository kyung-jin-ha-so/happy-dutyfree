package com.its.happy.service;

import com.its.happy.dto.OrderProductDTO;
import com.its.happy.entity.*;
import com.its.happy.repository.OrderProductRepository;
import com.its.happy.repository.OrderRepository;
import com.its.happy.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderProductService {
    private final OrderProductRepository orderProductRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderProductEntity findById(Long orderProductId) {
        Optional<OrderProductEntity> optionalOrderProductEntity = orderProductRepository.findById(orderProductId);
        if (optionalOrderProductEntity.isPresent()) {
            OrderProductEntity orderProductEntity = optionalOrderProductEntity.get();
            return orderProductEntity;
        }
        return null;
    }

    public void save(OrderProductDTO orderProductDTO) {
        Optional<OrderEntity> optionalOrderEntity = orderRepository.findById(orderProductDTO.getOrderId());
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(orderProductDTO.getProductId());
        if (optionalOrderEntity.isPresent()) {
            if (optionalProductEntity.isPresent()) {
                OrderEntity orderEntity = optionalOrderEntity.get();
                ProductEntity productEntity = optionalProductEntity.get();
                orderProductRepository.save(OrderProductEntity.toEntity(orderProductDTO, orderEntity, productEntity));
            }
        }
    }
}
