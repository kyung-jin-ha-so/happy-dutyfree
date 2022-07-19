package com.its.happy.entity;

import com.its.happy.dto.ProductFilesDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "product_files_table")
public class ProductFilesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_file_id")
    private Long productFileId;
    @Column(name = "product_file_name", length = 50, nullable = false)
    private String productFileName;

    // 상품파일(n)이 상품(1)을 참조함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;


    public static ProductFilesEntity toSaveEntity(ProductFilesDTO productFilesDTO, ProductEntity productEntity) {
        ProductFilesEntity productFilesEntity = new ProductFilesEntity();
        productFilesEntity.setProductFileName(productFilesDTO.getProductFileName());
        productFilesEntity.setProductEntity(productEntity);
        return productFilesEntity;
    }
}
