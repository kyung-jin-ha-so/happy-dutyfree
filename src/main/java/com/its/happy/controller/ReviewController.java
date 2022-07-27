package com.its.happy.controller;

import com.its.happy.dto.ReviewDTO;
import com.its.happy.entity.ReviewEntity;
import com.its.happy.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.dom4j.rule.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
    @PostMapping("/save")
    public String save(@ModelAttribute ReviewDTO reviewDTO, @RequestParam Long productId, @RequestParam Long memberId,
                       @RequestParam Long orderId){
        reviewService.save(reviewDTO, memberId, productId, orderId);
        return "redirect: /review/findByMemberId/" + memberId;
    }

    //save form테스트용
    @GetMapping("/saveFormTest")
    public String save(){
        return "/reviewPages/save";
    }

    @GetMapping("/findByMemberId/{memberId}")
    public String findByMemberId(@PathVariable Long memberId, Model model){
        List<ReviewDTO> reviewDTOList = reviewService.findByMemberId(memberId);
        model.addAttribute("reviewList", reviewDTOList);
        return "/reviewPages/list";
    }

    //후기 목록 test 용
    @GetMapping("/list")
    public String list(){
        return "/reviewPages/list";
    }

    //후기 삭제 test중
    @GetMapping("/delete/{reviewId}")
    public String reviewDelete(@PathVariable Long reviewId, HttpSession session){
        reviewService.deleteById(reviewId);
        Long memberId = (Long) session.getAttribute("loginId");
        return "redirect: /review/findByMemberId/" + memberId;
    }

}
