package com.its.happy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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
    private MultipartFile productThumbnailFile;
    private String productThumbnail;
    private Long productQuantity;
    private String productBrand;
    private String productStatus;



}
