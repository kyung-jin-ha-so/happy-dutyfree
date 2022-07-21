package com.its.happy.controller;

import com.its.happy.common.PagingConst;
import com.its.happy.dto.CategoryDTO;
import com.its.happy.dto.ProductDTO;
import com.its.happy.dto.ProductFilesDTO;
import com.its.happy.service.ProductFilesService;
import com.its.happy.service.ProductService;
import com.its.happy.service.ReviewService;
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
    private final ReviewService reviewService;
    private final ProductFilesService productFilesService;

    @GetMapping("/save")
    public String saveForm(){
        return "/productPages/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute ProductDTO productDTO, @RequestParam("productFile") List<MultipartFile> multipartFileList,
                       @ModelAttribute CategoryDTO categoryDTO) throws IOException {
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
        model.addAttribute("sort", "all");
        return "/productPages/list";
    }

    @GetMapping("/{categoryId}/")
    public String findByCategory(@PathVariable Long categoryId, @PageableDefault(page = 1) Pageable pageable, Model model){
        Page<ProductDTO> productList = productService.findByCategory(pageable, categoryId);
        model.addAttribute("productList", productList);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < productList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : productList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("sort", "basic");
        return "/productPages/list";
    }

    @GetMapping("/highPrice/{categoryId}/")
    public String findByHighPrice(@PathVariable Long categoryId, @PageableDefault(page = 1) Pageable pageable, Model model){
        Page<ProductDTO> productList = productService.findByHighPrice(pageable, categoryId);
        model.addAttribute("productList", productList);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < productList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : productList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("sort", "highPrice");
        return "/productPages/list";
    }

    @GetMapping("/lowPrice/{categoryId}/")
    public String findByLowPrice(@PathVariable Long categoryId, @PageableDefault(page = 1) Pageable pageable, Model model){
        Page<ProductDTO> productList = productService.findByLowPrice(pageable, categoryId);
        model.addAttribute("productList", productList);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < productList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : productList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("sort", "lowPrice");
        return "/productPages/list";
    }

    @GetMapping("/star/{categoryId}/")
    public String findByStar(@PathVariable Long categoryId, @PageableDefault(page = 1) Pageable pageable, Model model){
        Page<ProductDTO> productList = productService.findByStar(pageable, categoryId);
        model.addAttribute("productList", productList);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < productList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : productList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("sort", "star");
        return "/productPages/list";
    }

    @GetMapping("/detail/{productId}")
    public String findById(@PathVariable Long productId, Model model){
        ProductDTO productDTO= productService.findById(productId);
        model.addAttribute("product", productDTO);
        return "/productPages/detail";
    }

    @GetMapping("/statusClose/{productId}")
    public String statusClose(@PathVariable Long productId){
        productService.statusClose(productId);
        return "redirect:/admin/productList/";
    }

    @GetMapping("/statusOpen/{productId}")
    public String statusOpen(@PathVariable Long productId){
        productService.statusOpen(productId);
        return "redirect:/admin/productList/";
    }

    @PostMapping("/changeQuantity")
    public String changeQuantity(@ModelAttribute ProductDTO productDTO){
        productService.changeQuantity(productDTO);
        return "redirect:/admin/productList/";
    }

    @GetMapping("/update/{productId}")
    public String updateForm(@PathVariable Long productId, Model model){
        ProductDTO productDTO = productService.findById(productId);
        model.addAttribute("product", productDTO);
        return "productPages/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute ProductDTO productDTO, @RequestParam("productFile") List<MultipartFile> multipartFileList,
                       @ModelAttribute CategoryDTO categoryDTO) throws IOException {
        Long updatedId = productService.update(productDTO, categoryDTO);
//        productService.fileUpdate(updatedId, multipartFileList);
        return "index";
    }

}
