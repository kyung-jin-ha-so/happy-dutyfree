package com.its.happy.dto;

import com.its.happy.entity.ProductEntity;
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


    public static ProductDTO toDTO(ProductEntity productEntity) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(productEntity.getProductId());
        productDTO.setProductName(productEntity.getProductName());
        productDTO.setProductDesc(productEntity.getProductDesc());
        productDTO.setProductOriginalPrice(productEntity.getProductOriginalPrice());
        productDTO.setProductDiscount(productEntity.getProductDiscount());
        productDTO.setProductPrice(productEntity.getProductPrice());
        productDTO.setProductThumbnail(productEntity.getProductThumbnail());
        productDTO.setProductStar(productEntity.getProductStar());
        productDTO.setProductQuantity(productEntity.getProductQuantity());
        productDTO.setProductStatus(productEntity.getProductStatus());
        return productDTO;
    }

    public ProductDTO(Long productId, String productName, double productOriginalPrice, Long productDiscount, double productPrice, double productStar, String productThumbnail, Long productQuantity, String productBrand, String productStatus) {
        this.productId = productId;
        this.productName = productName;
        this.productOriginalPrice = productOriginalPrice;
        this.productDiscount = productDiscount;
        this.productPrice = productPrice;
        this.productStar = productStar;
        this.productThumbnail = productThumbnail;
        this.productQuantity = productQuantity;
        this.productBrand = productBrand;
        this.productStatus = productStatus;
    }
}
