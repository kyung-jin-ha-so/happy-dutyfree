package com.its.happy.service;

import com.its.happy.dto.ProductFilesDTO;
import com.its.happy.entity.ProductFilesEntity;
import com.its.happy.repository.ProductFilesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductFilesService {

    private final ProductFilesRepository productFilesRepository;

    public List<ProductFilesDTO> findByProductId(Long productId) {
        List<ProductFilesEntity> productFilesEntityList= productFilesRepository.findByProductEntityProductId(productId);
        List<ProductFilesDTO> productFilesDTOList = new ArrayList<>();
        for (ProductFilesEntity productFile:productFilesEntityList) {
            productFilesDTOList.add(ProductFilesDTO.toDTO(productFile));
        }return productFilesDTOList;
    }

    public void deleteById(Long productFileId) {
        productFilesRepository.deleteById(productFileId);
    }
}
