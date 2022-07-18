package com.its.happy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long productId;
    private String productName;
    private String productDesc;
    private double productOriginalPrice;;
    private Long productDiscount;
    private double productPrice;
    private double productStar;
    private String productThumbnail;
    private Long productQuantity;
    private String productBrand;
    private String productStatus;



}
