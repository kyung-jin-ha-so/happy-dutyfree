package com.its.happy.entity;

import com.its.happy.dto.ProductDTO;
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
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ReviewEntity> reviewEntityList = new ArrayList<>();

    // 상품(1)이 찜(n)에게 참조당함
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LikeEntity> likeEntityList = new ArrayList<>();

    // 상품(1)이 장바구니(n)에게 참조당함

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<CartEntity> cartEntityList = new ArrayList<>();

    // 상품(1)이 주문상품(n)에게 참조당함

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<OrderProductEntity> orderProductEntityList = new ArrayList<>();

    public static ProductEntity toSaveEntity(ProductDTO productDTO, CategoryEntity categoryEntity) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductName(productDTO.getProductName());
        productEntity.setProductDesc(productDTO.getProductDesc());
        productEntity.setProductOriginalPrice(productDTO.getProductOriginalPrice());
        productEntity.setProductDiscount(productDTO.getProductDiscount());
        productEntity.setProductPrice(productDTO.getProductPrice());
        productEntity.setProductStar(productDTO.getProductStar());
        productEntity.setProductThumbnail(productDTO.getProductThumbnail());
        productEntity.setProductQuantity(productDTO.getProductQuantity());
        productEntity.setCategoryEntity(categoryEntity);
        productEntity.setProductBrand(productDTO.getProductBrand());
        productEntity.setProductStatus("판매중");
        productEntity.setProductFilesEntityList(productDTO.getProductFilesEntityList());
        return productEntity;
    }

    public static ProductEntity toUpdateEntity(ProductDTO productDTO, CategoryEntity categoryEntity) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductId(productDTO.getProductId());
        productEntity.setProductName(productDTO.getProductName());
        productEntity.setProductDesc(productDTO.getProductDesc());
        productEntity.setProductOriginalPrice(productDTO.getProductOriginalPrice());
        productEntity.setProductDiscount(productDTO.getProductDiscount());
        productEntity.setProductPrice(productDTO.getProductPrice());
        productEntity.setProductStar(productDTO.getProductStar());
        productEntity.setProductThumbnail(productDTO.getProductThumbnail());
        productEntity.setProductQuantity(productDTO.getProductQuantity());
        productEntity.setCategoryEntity(categoryEntity);
        productEntity.setProductBrand(productDTO.getProductBrand());
        productEntity.setProductStatus("판매중");
        productEntity.setProductFilesEntityList(productDTO.getProductFilesEntityList());
        return productEntity;
    }


    @PreRemove
    private void preRemove() {
        cartEntityList.forEach(cart -> cart.setProductEntity(null));
        orderProductEntityList.forEach(orderProduct -> orderProduct.setProductEntity(null));
        productFilesEntityList.forEach(productFiles -> productFiles.setProductEntity(null));
    }

}
