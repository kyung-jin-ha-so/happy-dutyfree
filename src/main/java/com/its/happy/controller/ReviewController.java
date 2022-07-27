package com.its.happy.controller;

import com.its.happy.dto.ReviewDTO;
import com.its.happy.entity.ReviewEntity;
import com.its.happy.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.dom4j.rule.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;


    @GetMapping("/save/{orderProductId}")
    public String saveForm(@PathVariable Long orderProductId, Model model){
        ReviewEntity reviewEntity = reviewService.findById(orderProductId);
        model.addAttribute("productId", reviewEntity.getProductEntity().getProductId());
        model.addAttribute("productName", reviewEntity.getProductEntity().getProductName());
        model.addAttribute("memberId", reviewEntity.getMemberEntity().getMemberId());
        model.addAttribute("orderId", reviewEntity.getOrderEntity().getOrderId());
        return "/reviewPages/save";
    }

    @GetMapping("/saveFormTest")
    public String save(){
        return "/reviewPages/save";
    }

//    @GetMapping("/findByMemberId/{memberId}")
//    public String findByMemberId(@PathVariable Long memberId){
//        List<ReviewDTO> reviewDTOList = reviewService.findByMemberId(memberId);
//    }
}
