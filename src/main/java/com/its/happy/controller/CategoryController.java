package com.its.happy.controller;

import com.its.happy.dto.CategoryDTO;
import com.its.happy.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/save")
    public String saveForm(){
        return "/productPages/categorySave";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute CategoryDTO categoryDTO){
        Long categoryId = categoryService.save(categoryDTO);
        System.out.println("categoryId = " + categoryId);
        return "index";
    }
}
