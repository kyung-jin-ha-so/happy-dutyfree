package com.its.happy.controller;

import com.its.happy.dto.CategoryDTO;
import com.its.happy.dto.ExchangeRateDTO;
import com.its.happy.dto.MemberDTO;
import com.its.happy.dto.ProductDTO;
import com.its.happy.entity.CategoryEntity;
import com.its.happy.entity.ProductEntity;
import com.its.happy.repository.CategoryRepository;
import com.its.happy.repository.ExchangeRateRepository;
import com.its.happy.repository.ProductRepository;
import com.its.happy.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;
    private final CategoryService categoryService;

    private final ExchangeRateService exchangeRateService;
    private final MemberService memberService;
    private final OrderService orderService;

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/")
    public String main(Model model, HttpSession session){
        List<CategoryDTO> categoryDTOList = categoryService.findAll();
        List<ProductDTO> productDTOList = productService.findMainAll();
        ExchangeRateDTO exchangeRateDTO = exchangeRateService.findByDate();
        model.addAttribute("categoryList", categoryDTOList);
        model.addAttribute("productList", productDTOList);
        session.setAttribute("exchangeRate", exchangeRateDTO.getExchangeRate());
        return "main";
    }

    @GetMapping("/myPageMain")
    public String myPageMain(HttpSession session,Model model){
        Long memberId = (Long) session.getAttribute("loginId");
        MemberDTO memberDTO = memberService.findById(memberId);
        model.addAttribute("member",memberDTO);
        return "/myPages/main";
    }

    @GetMapping("/adminMain")
    public String adminMain(Model model){
        double todayRevenue = orderService.findTodayRevenue();
        model.addAttribute("todayRev", todayRevenue);
        return "adminPages/main";
    }

}
