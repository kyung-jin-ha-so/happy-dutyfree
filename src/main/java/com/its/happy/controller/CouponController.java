package com.its.happy.controller;

import com.its.happy.dto.CouponDTO;
import com.its.happy.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class CouponController {
    private final CouponService couponService;
    //쿠폰 저장 페이지 이동
    @RequestMapping("/saveForm")
    public String saveForm(){
        return "couponPages/save";
    }
    //쿠폰 저장
    @RequestMapping("/save")
    public String save(@ModelAttribute CouponDTO couponDTO)throws IOException {
        couponService.save(couponDTO);
        return "index";
    }

}
