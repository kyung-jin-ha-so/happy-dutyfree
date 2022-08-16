package com.its.happy.controller;

import com.its.happy.common.PagingConst;
import com.its.happy.dto.*;
import com.its.happy.dto.CategoryDTO;
import com.its.happy.dto.ProductDTO;
import com.its.happy.service.CartService;
import com.its.happy.service.ProductFilesService;
import com.its.happy.service.ProductService;
import com.its.happy.service.ReviewService;
import com.its.happy.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@RequestMapping("/product")
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductFilesService productFilesService;
    private final CartService cartService;
    private final CategoryService categoryService;
    private final ExchangeRateService exchangeRateService;
    private final MemberService memberService;

    //상품 등록 페이지 이동
    @GetMapping("/save")
    public String saveForm(Model model) {
        List<CategoryDTO> categoryDTOList = categoryService.findAll();
        model.addAttribute("categoryDTOList", categoryDTOList);
        return "/productPages/save";
    }

    //상품 등록
    @PostMapping("/save")
    public String save(@ModelAttribute ProductDTO productDTO, @RequestParam("productFile") List<MultipartFile> multipartFileList,
                       @ModelAttribute CategoryDTO categoryDTO) throws IOException {
        Long savedId = productService.save(productDTO, categoryDTO);
        productService.fileSave(savedId, multipartFileList);
        return "redirect:/";
    }

    //상품 목록 (카테고리 x)
    @GetMapping("/")
    public String findAll(@PageableDefault(page = 1) Pageable pageable,
                          @RequestParam(defaultValue = "5", required = false) int pageLimit, Model model) {
        Page<ProductDTO> productList = productService.findAll(pageable, pageLimit);
        long count = productService.count();
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1)     < productList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : productList.getTotalPages();
        model.addAttribute("productList", productList);
        model.addAttribute("count", count);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("sort", "all");
        return "/productPages/list";
    }

    //상품목록 (카테고리별)
    @GetMapping("/{categoryId}/")
    public String findByCategory(@PathVariable Long categoryId, @RequestParam(defaultValue = "5", required = false) int pageLimit,
                                 @PageableDefault(page = 1) Pageable pageable, Model model) {
        Page<ProductDTO> productList = productService.findByCategory(pageable, categoryId, pageLimit);
        long count = productService.countByCategoryId(categoryId);
        CategoryDTO categoryDTO = categoryService.findById(categoryId);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < productList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : productList.getTotalPages();
        if(startPage == 0 || endPage == 0){
            startPage = 1; endPage = 1;
        }
        model.addAttribute("productList", productList);
        model.addAttribute("count", count);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("categoryDTO", categoryDTO);
        model.addAttribute("sort", "basic");
        model.addAttribute("pageLimit", pageLimit);
        return "/productPages/list";
    }

    //가격 높은 순으로 상품 목록 출력
    @GetMapping("/highPrice/{categoryId}/")
    public String findByHighPrice(@PathVariable Long categoryId, @RequestParam(defaultValue = "5", required = false) int pageLimit,
                                  @PageableDefault(page = 1) Pageable pageable, Model model) {
        Page<ProductDTO> productList = productService.findByHighPrice(pageable, categoryId, pageLimit);
        model.addAttribute("productList", productList);
        long count = productService.countByCategoryId(categoryId);
        CategoryDTO categoryDTO = categoryService.findById(categoryId);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < productList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : productList.getTotalPages();
        if(startPage == 0 || endPage == 0){
            startPage = 1; endPage = 1;
        }
        model.addAttribute("count", count);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("sort", "highPrice");
        model.addAttribute("categoryDTO", categoryDTO);
        model.addAttribute("pageLimit", pageLimit);
        return "/productPages/list";
    }
    //가격 낮은 순으로 상품 목록 출력
    @GetMapping("/lowPrice/{categoryId}/")
    public String findByLowPrice(@PathVariable Long categoryId,  @RequestParam(defaultValue = "5", required = false) int pageLimit,
                                 @PageableDefault(page = 1) Pageable pageable, Model model) {
        Page<ProductDTO> productList = productService.findByLowPrice(pageable, categoryId, pageLimit);
        model.addAttribute("productList", productList);
        long count = productService.countByCategoryId(categoryId);
        CategoryDTO categoryDTO = categoryService.findById(categoryId);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < productList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : productList.getTotalPages();
        if(startPage == 0 || endPage == 0){
            startPage = 1; endPage = 1;
        }
        model.addAttribute("count", count);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("categoryDTO", categoryDTO);
        model.addAttribute("sort", "lowPrice");
        model.addAttribute("pageLimit", pageLimit);
        return "/productPages/list";
    }

    // 별점 높은 순으로 상품 목록 출력
    @GetMapping("/star/{categoryId}/")
    public String findByStar(@PathVariable Long categoryId, @RequestParam(defaultValue = "5", required = false) int pageLimit,
                             @PageableDefault(page = 1) Pageable pageable, Model model) {
        Page<ProductDTO> productList = productService.findByStar(pageable, categoryId, pageLimit);
        model.addAttribute("productList", productList);
        long count = productService.countByCategoryId(categoryId);
        CategoryDTO categoryDTO = categoryService.findById(categoryId);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < productList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : productList.getTotalPages();
        if(startPage == 0 || endPage == 0){
            startPage = 1; endPage = 1;
        }
        model.addAttribute("count", count);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("sort", "star");
        model.addAttribute("categoryDTO", categoryDTO);
        model.addAttribute("pageLimit", pageLimit);
        return "/productPages/list";
    }

    // 상품 상세 페이지 보기
    @GetMapping("/detail/{productId}")
    public String findById(@PathVariable Long productId, Model model, HttpSession session) {
        ProductDTO productDTO = productService.findById(productId);
        model.addAttribute("product", productDTO);
        Long memberId = (Long) session.getAttribute("loginId");
        LikeDTO likeDTO = productService.findByLike(productId, memberId);
        model.addAttribute("like", likeDTO);
        CartDTO cartDTO = cartService.findByCart(productId, memberId);
        model.addAttribute("cart", cartDTO);
        return "/productPages/detail";
    }

    //상품 상태 판매중지로 변경
    @GetMapping("/statusClose/{productId}")
    public String statusClose(@PathVariable Long productId) {
        productService.statusClose(productId);
        return "redirect:/admin/productList/";
    }

    //상품 상태 판매중으로 변경
    @GetMapping("/statusOpen/{productId}")
    public String statusOpen(@PathVariable Long productId) {
        productService.statusOpen(productId);
        return "redirect:/admin/productList/";
    }

    //상품 수량 변경
    @PostMapping("/changeQuantity")
    public String changeQuantity(@ModelAttribute ProductDTO productDTO) {
        productService.changeQuantity(productDTO);
        return "redirect:/admin/productList/";
    }

    //상품 수정
    @GetMapping("/update/{productId}")
    public String updateForm(@PathVariable Long productId, Model model) {
        ProductDTO productDTO = productService.findById(productId);
        model.addAttribute("categoryDTOList", categoryService.findAll());
        model.addAttribute("product", productDTO);
        return "productPages/update";
    }

    //상품 수정
    @PostMapping("/update")
    public String update(@ModelAttribute ProductDTO productDTO, @RequestParam("productFile") List<MultipartFile> multipartFileList,
                         @ModelAttribute CategoryDTO categoryDTO) throws IOException {
        Long updatedId = productService.update(productDTO, categoryDTO);
        productService.fileSave(updatedId, multipartFileList);
        return "redirect:/";
    }

    //상품 상세페이지 이미지 삭제
    @PostMapping("/deleteFile")
    public @ResponseBody String deleteFile(@RequestParam("productFileId") Long productFileId) {
        productFilesService.deleteById(productFileId);
        return "삭제";
    }

    //상품 검색으로 목록 보여주기
    @GetMapping("/search/")
    public String search(@RequestParam("q") String q,
                         @RequestParam(name = "del", defaultValue = "nd",required = false) String del,
                         @PageableDefault(page = 1) Pageable pageable, Model model, HttpSession session){
        Long loginId = (Long) session.getAttribute("loginId");
        Page<ProductDTO> productList = null;
        if(loginId != null){
            productList = productService.findSearch(pageable, q, loginId, del);
        } else {
            productList = productService.findSearchWithoutId(pageable,q, del);
        }
        model.addAttribute("productList", productList);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < productList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : productList.getTotalPages();
        if(startPage == 0 || endPage == 0){
            startPage = 1; endPage = 1;
        }
        long count = productService.countSearch(q);
        model.addAttribute("count", count);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("q", q);
        model.addAttribute("sort", "search");
        return "/productPages/list";
    }

    //상품 찜하기
    @PostMapping("/like")
    public ResponseEntity like(@RequestParam("productId") Long productId,
                               @RequestParam("memberId") Long memberId) {
        String result = productService.like(productId, memberId);
        System.out.println("result = " + result);
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
    //회원별 찜 리스트 보기
    @GetMapping("/likeList")
    public String likeList(HttpSession session, Model model){
        Long memberId = (Long) session.getAttribute("loginId");
        List<LikeDTO> likeDTOList = productService.findByLikeList(memberId);
        model.addAttribute("likeList", likeDTOList);
        ExchangeRateDTO exchangeRateDTO = exchangeRateService.findByDate();
        model.addAttribute("exchangeRateDTO", exchangeRateDTO);
        MemberDTO memberDTO = memberService.findById(memberId);
        model.addAttribute("member", memberDTO);
        return "/productPages/likeList";
    }

    @PostMapping("/detailAjax")
    public @ResponseBody ProductDTO findByIdAjax(@RequestParam Long productId){
        ProductDTO productDTO = productService.findById(productId);
        return productDTO;
    }
}

