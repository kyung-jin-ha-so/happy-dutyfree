package com.its.happy.controller;

import com.its.happy.common.PagingConst;
import com.its.happy.dto.*;
import com.its.happy.dto.CategoryDTO;
import com.its.happy.dto.ProductDTO;
import com.its.happy.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RequestMapping("/product")
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ReviewService reviewService;
    private final ProductFilesService productFilesService;
    private final CartService cartService;

    private final CategoryService categoryService;


    @GetMapping("/save")
    public String saveForm(Model model) {
        List<CategoryDTO> categoryDTOList = categoryService.findAll();
        model.addAttribute("categoryDTOList", categoryDTOList);
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
    public String findAll(@PageableDefault(page = 1) Pageable pageable, Model model) {
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
    public String findByCategory(@PathVariable Long categoryId, @PageableDefault(page = 1) Pageable pageable, Model model) {
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
    public String findByHighPrice(@PathVariable Long categoryId, @PageableDefault(page = 1) Pageable pageable, Model model) {
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
    public String findByLowPrice(@PathVariable Long categoryId, @PageableDefault(page = 1) Pageable pageable, Model model) {
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
    public String findByStar(@PathVariable Long categoryId, @PageableDefault(page = 1) Pageable pageable, Model model) {
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
    public String findById(@PathVariable Long productId, Model model, HttpSession session) {
        ProductDTO productDTO = productService.findById(productId);
        model.addAttribute("product", productDTO);
        Long memberId = (Long) session.getAttribute("loginId");
        LikeDTO likeDTO = productService.findByLike(productId, memberId);
        model.addAttribute("like", likeDTO);
        System.out.println("likeDTO = " + likeDTO);
        CartDTO cartDTO = cartService.findByCart(productId, memberId);
        model.addAttribute("cart", cartDTO);
        return "/productPages/detail";
    }

    @GetMapping("/statusClose/{productId}")
    public String statusClose(@PathVariable Long productId) {
        productService.statusClose(productId);
        return "redirect:/admin/productList/";
    }

    @GetMapping("/statusOpen/{productId}")
    public String statusOpen(@PathVariable Long productId) {
        productService.statusOpen(productId);
        return "redirect:/admin/productList/";
    }

    @PostMapping("/changeQuantity")
    public String changeQuantity(@ModelAttribute ProductDTO productDTO) {
        productService.changeQuantity(productDTO);
        return "redirect:/admin/productList/";
    }

    @GetMapping("/update/{productId}")
    public String updateForm(@PathVariable Long productId, Model model) {
        ProductDTO productDTO = productService.findById(productId);
        model.addAttribute("categoryDTOList", categoryService.findAll());
        model.addAttribute("product", productDTO);
        return "productPages/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute ProductDTO productDTO, @RequestParam("productFile") List<MultipartFile> multipartFileList,
                         @ModelAttribute CategoryDTO categoryDTO) throws IOException {
        Long updatedId = productService.update(productDTO, categoryDTO);
        productService.fileSave(updatedId, multipartFileList);
        return "index";
    }

    @PostMapping("/deleteFile")
    public @ResponseBody String deleteFile(@RequestParam("productFileId") Long productFileId) {
        System.out.println("productFileId = " + productFileId);
        productFilesService.deleteById(productFileId);
        return "삭제";
    }

    @GetMapping("/search/")
    public String search(@RequestParam("q") String q, @PageableDefault(page = 1) Pageable pageable, Model model) {
        Page<ProductDTO> productList = productService.findSearch(pageable, q);
        model.addAttribute("productList", productList);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < productList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : productList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
//        model.addAttribute("q", q);
        model.addAttribute("sort", "search");
        return "/productPages/list";
    }

    //상품 찜하기
    @PostMapping("/like")
    public ResponseEntity like(@RequestParam("productId") Long productId,
                               @RequestParam("memberId") Long memberId) {
        String result = productService.like(productId, memberId);
        if (result == "ok") {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    //상품 찜 취소하기
    @PostMapping("/dontLike")
    public @ResponseBody boolean dontLike(@RequestParam("productId") Long productId, @RequestParam("memberId") Long memberId) {
        productService.deleteById(memberId, productId);
        return true;
    }
    //회원별 상품 리스트 보기
    @GetMapping("/likeList")
    public String likeList(HttpSession session, Model model){
        Long memberId = (Long) session.getAttribute("loginId");
        List<LikeDTO> likeDTOList = productService.findByLikeList(memberId);
        model.addAttribute("likeList", likeDTOList);
        return "/productPages/likeList";
    }
}

