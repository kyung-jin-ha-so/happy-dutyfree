package com.its.happy.dto;

import com.its.happy.entity.CategoryEntity;
import lombok.Data;

@Data
public class CategoryDTO {

    private Long categoryId;
    private String categoryName;

    public static CategoryDTO toDTO(CategoryEntity categoryEntity) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(categoryEntity.getCategoryId());
        categoryDTO.setCategoryName(categoryEntity.getCategoryName());
        return categoryDTO;
    }
}
