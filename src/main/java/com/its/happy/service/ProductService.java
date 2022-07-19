package com.its.happy.service;

import com.its.happy.dto.CategoryDTO;
import com.its.happy.dto.ProductDTO;
import com.its.happy.dto.ProductFilesDTO;
import com.its.happy.entity.CategoryEntity;
import com.its.happy.entity.ProductEntity;
import com.its.happy.entity.ProductFilesEntity;
import com.its.happy.repository.CategoryRepository;
import com.its.happy.repository.ProductFilesRepository;
import com.its.happy.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.IOException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductFilesRepository productFilesRepository;


    public Long save(ProductDTO productDTO, CategoryDTO categoryDTO) throws IOException {
        MultipartFile productThumbnailFile = productDTO.getProductThumbnailFile();
        String productThumbnail = productThumbnailFile.getOriginalFilename();
        productThumbnail = System.currentTimeMillis() + "-" + productThumbnail;
        String savePath = "C:\\happy_img\\" + productThumbnail;
        if (!productThumbnailFile.isEmpty()) {
            productThumbnailFile.transferTo(new File(savePath));
        }
        productDTO.setProductThumbnail(productThumbnail);
        Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(categoryDTO.getCategoryId());
        if (optionalCategoryEntity.isPresent()) {
            CategoryEntity categoryEntity = optionalCategoryEntity.get();
            Long savedId = productRepository.save(ProductEntity.toSaveEntity(productDTO, categoryEntity)).getProductId();
            return savedId;
        }
        return null;
    }


    public void fileSave(Long savedId, List<MultipartFile> multipartFileList) throws IOException {
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(savedId);
        System.out.println("optionalProductEntity = " + optionalProductEntity);
        if(optionalProductEntity.isPresent()){
            ProductEntity productEntity = optionalProductEntity.get();
            ProductFilesDTO productFilesDTO = new ProductFilesDTO();
            for (MultipartFile file : multipartFileList) {
                String productFileName = file.getOriginalFilename();
                productFileName = System.currentTimeMillis() + "-" + productFileName;
                String savePath = "C:\\happy_img\\" + productFileName;
                if (!file.isEmpty()) {
                    file.transferTo(new File(savePath));
                    productFilesDTO.setProductFileName(productFileName);
                    productFilesRepository.save(ProductFilesEntity.toSaveEntity(productFilesDTO, productEntity));
                }
            }
        }
    }
}
