package com.its.happy.service;

import com.its.happy.dto.CategoryDTO;
import com.its.happy.entity.CategoryEntity;
import com.its.happy.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Long save(CategoryDTO categoryDTO) {
        return categoryRepository.save(CategoryEntity.toSaveEntity(categoryDTO)).getCategoryId();
    }

    public List<CategoryDTO> findAll() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for (CategoryEntity category:categoryEntityList) {
            categoryDTOList.add(CategoryDTO.toDTO(category));
        }
        return categoryDTOList;
    }

    public CategoryDTO findById(Long categoryId) {
        Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(categoryId);
        if(optionalCategoryEntity.isPresent()){
            CategoryEntity categoryEntity = optionalCategoryEntity.get();
            return CategoryDTO.toDTO(categoryEntity);
        }return null;
    }
}
