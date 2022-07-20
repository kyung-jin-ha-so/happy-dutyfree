package com.its.happy.controller;

import com.its.happy.dto.CouponDTO;
import com.its.happy.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class CouponController {
    private final CouponService couponService;
    //쿠폰 저장 페이지 이동
    @GetMapping("/saveForm")
    public String saveForm(){
        return "couponPages/save";
    }
    //쿠폰 저장
    @PostMapping("/save")
    public String save(@ModelAttribute CouponDTO couponDTO)throws IOException {
        couponService.save(couponDTO);
        return "index";
    }
    //쿠폰 리스트 조회
    @GetMapping("/findAll")
    public String findAll(Model model){
        List<CouponDTO> couponDTOList = couponService.findAll();
        model.addAttribute("couponList", couponDTOList);
        System.out.println("CouponController.findAll");
        System.out.println("couponDTOList = " + couponDTOList);
        return "/couponPages/list";
    }

}
