package com.its.happy.controller;

import com.its.happy.dto.CouponDTO;
import com.its.happy.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
    //쿠폰발급
    @PostMapping("/issueCoupon")
    public ResponseEntity issueCoupon(@RequestParam("memberId") Long memberId, @RequestParam("couponId") Long couponId, @RequestParam("today") String today){
        String result = couponService.issueCoupon(couponId, memberId, today);
        System.out.println("result = " + result);
        if(result.equals("ok")){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
