package com.its.happy.entity;

import lombok.Getter;
import lombok.Setter;

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
    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(name = "product_desc")
    private String productDesc;
    @Column(name = "product_original_price" , nullable = false)
    private double productOriginalPrice;
    @Column(name = "product_discount")
    private Long productDiscount;
    @Column(name = "productPrice" , nullable = false)
    private Long productPrice;
    @Column(name = "productStar")
    private double productStar;
    @Column(name = "product_thumbnail")
    private String productThumbnail;
    @Column(name = "product_quantity")
    private Long productQuantity;
    @Column(name = "product_brand")
    private String productBrand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryEntity;

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductFilesEntity> productFilesEntityList = new ArrayList<>();
}
