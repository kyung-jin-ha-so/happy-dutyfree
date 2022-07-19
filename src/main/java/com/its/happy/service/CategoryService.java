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

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Long save(CategoryDTO categoryDTO) {
        return categoryRepository.save(CategoryEntity.toSaveEntity(categoryDTO)).getCategoryId();
    }
}
