package com.its.happy.controller;

import com.its.happy.dto.CouponDTO;
import com.its.happy.dto.CouponMemberDTO;
import com.its.happy.dto.EventDTO;
import com.its.happy.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
    //쿠폰발급
    @PostMapping("/issueCoupon")
    public ResponseEntity issueCoupon(@RequestParam("memberId") Long memberId, @RequestParam("couponId") Long couponId){
        String result = couponService.issueCoupon(couponId, memberId);
        System.out.println("result = " + result);
        if(result.equals("ok")){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    //쿠폰 리스트 조회(조회 및 관리용)
    @GetMapping("/couponList")
    public String couponList(Model model){
        List<CouponDTO> couponDTOList = couponService.findAll();
        model.addAttribute("couponList", couponDTOList);
        return "/couponPages/list";
    }
    //쿠폰 삭제
    @GetMapping("/delete/{couponId}")
    public String delete(@PathVariable Long couponId){
        couponService.deleteById(couponId);
        return "redirect:/coupon/couponList";
    }
    //회원별 쿠폰리스트 조회
    @GetMapping("/myCouponList")
    public String myCouponList(Model model, HttpSession session){
        Long memberId = (Long) session.getAttribute("loginId");
        List<CouponMemberDTO> couponMemberDTOList  = couponService.findByMyCoupon(memberId);
        model.addAttribute("myCoupon", couponMemberDTOList);
        return "/couponPages/myCoupon";
    }
    //쿠폰 수정 화면 요청
    @GetMapping("/update-form/{couponId}")
    public String update(@PathVariable Long couponId, Model model){
        CouponDTO couponDTO = couponService.findByCouponId(couponId);
        model.addAttribute("coupon", couponDTO);
        return "/couponPages/update";
    }
    //쿠폰 수정
//    @PostMapping("/update")
//    public String update(@ModelAttribute CouponDTO couponDTO){
//        couponService.update(couponDTO);
//        return "redirect:/coupon/couponList";
//    }
}
