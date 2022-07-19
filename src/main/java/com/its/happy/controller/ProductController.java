package com.its.happy.controller;

import com.its.happy.common.PagingConst;
import com.its.happy.dto.CategoryDTO;
import com.its.happy.dto.ProductDTO;
import com.its.happy.dto.ProductFilesDTO;
import com.its.happy.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/product")
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/save")
    public String saveForm(){
        return "/productPages/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute ProductDTO productDTO, @RequestParam("productFile") List<MultipartFile> multipartFileList,
                       @ModelAttribute CategoryDTO categoryDTO) throws IOException {
        System.out.println("productDTO = " + productDTO + ", multipartFileList = " + multipartFileList + ", categoryDTO = " + categoryDTO);
        Long savedId = productService.save(productDTO, categoryDTO);
        productService.fileSave(savedId, multipartFileList);
        return "index";
    }

    @GetMapping("/")
    public String findAll(@PageableDefault(page = 1) Pageable pageable, Model model){
        Page<ProductDTO> productList = productService.findAll(pageable);
        model.addAttribute("productList", productList);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < productList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : productList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "/productPages/list";
    }

}
