package com.its.happy.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "product_table")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "product_name", nullable = false, length = 30)
    private String productName;
    @Column(name = "product_desc", length = 500)
    private String productDesc;
    @Column(name = "product_original_price" , nullable = false)
    private double productOriginalPrice;
    @Column(name = "product_discount")
    private Long productDiscount;
    @Column(name = "productPrice" , nullable = false)
    private double productPrice;
    @Column(name = "productStar")
    @ColumnDefault("0")
    private double productStar;
    @Column(name = "product_thumbnail")
    private String productThumbnail;
    @Column(name = "product_quantity")
    private Long productQuantity;
    @Column(name = "product_brand")
    private String productBrand;
    @Column(name = "product_status")
    private String productStatus;

    // 상품(n)이 카테고리(1)를 참조함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryEntity;

    // 상품(1)이 상품파일(n)에게 참조당함
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<ProductFilesEntity> productFilesEntityList = new ArrayList<>();

    // 상품(1)이 상품후기(n)에게 참조당함
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<ReviewEntity> reviewEntityList = new ArrayList<>();

    // 상품(1)이 찜(n)에게 참조당함
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<LikeEntity> likeEntityList = new ArrayList<>();

    // 상품(1)이 장바구니(n)에게 참조당함

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<CartEntity> cartEntityList = new ArrayList<>();

    // 상품(1)이 주문상품(n)에게 참조당함

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<OrderProductEntity> orderProductEntityList = new ArrayList<>();

    @PreRemove
    private void preRemove() {
        productFilesEntityList.forEach(productFiles -> productFiles.setProductEntity(null));
        reviewEntityList.forEach(review -> review.setProductEntity(null));
        likeEntityList.forEach(like -> like.setProductEntity(null));
        cartEntityList.forEach(cart -> cart.setProductEntity(null));
        orderProductEntityList.forEach(orderProduct -> orderProduct.setProductEntity(null));
    }

}
