package com.its.happy.dto;

import com.its.happy.entity.ProductFilesEntity;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
public class ProductFilesDTO {

    private Long productFileId;
    private MultipartFile productFile;
    private String productFileName;

    public static ProductFilesDTO toDTO(ProductFilesEntity ProductFilesEntity) {
    ProductFilesDTO productFilesDTO = new ProductFilesDTO();
        productFilesDTO.setProductFileName(ProductFilesEntity.getProductFileName());
        productFilesDTO.setProductFileId(ProductFilesEntity.getProductFileId());
        return productFilesDTO;
    }
}
