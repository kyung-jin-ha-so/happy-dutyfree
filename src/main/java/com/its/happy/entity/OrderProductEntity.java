package com.its.happy.entity;

import com.its.happy.dto.OrderProductDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "order_product_table")
public class OrderProductEntity {
    // 주문상품 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long orderProductId;

    // 상품 정가
    @Column(nullable = false)
    private double productOriginalPrice;

    // 상품 할인율
    @Column(nullable = false)
    private Long productDiscount;

    // 주문 수량
    @Column(nullable = false)
    private int orderQty;

    // 상품 할인가
    @Column(nullable = false)
    private double productPrice;

    // 상품 이름
    @Column(nullable = false)
    private String productName;

    // OrderProductEntity(N)가 OrderEntity(1)을 참조함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;

    // OrderProductEntity(N)가 ProductEntity(1)을 참조함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    public static OrderProductEntity toEntity(OrderProductDTO orderProductDTO, OrderEntity orderEntity, ProductEntity productEntity) {
        OrderProductEntity orderProductEntity = new OrderProductEntity();
        orderProductEntity.setOrderQty(orderProductDTO.getOrderQty());
        orderProductEntity.setProductDiscount(orderProductDTO.getProductDiscount());
        orderProductEntity.setProductOriginalPrice(orderProductDTO.getProductOriginalPrice());
        orderProductEntity.setOrderEntity(orderEntity);
        orderProductEntity.setProductEntity(productEntity);
        orderProductEntity.setProductPrice(orderProductDTO.getProductPrice());
        orderProductEntity.setProductName(orderProductDTO.getProductName());
        return orderProductEntity;
    }
}
