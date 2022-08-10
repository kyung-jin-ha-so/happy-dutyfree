package com.its.happy.controller;

import com.its.happy.dto.ReviewDTO;
import com.its.happy.entity.OrderProductEntity;
import com.its.happy.entity.ReviewEntity;
import com.its.happy.repository.OrderProductRepository;
import com.its.happy.service.OrderProductService;
import com.its.happy.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.dom4j.rule.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
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
    private final OrderProductService orderProductService;

    //후기 저장하는 폼 보여주기
    @GetMapping("/save/{orderProductId}")
    public String saveForm(@PathVariable Long orderProductId, Model model){
        OrderProductEntity orderProductEntity = orderProductService.findById(orderProductId);
        model.addAttribute("productId", orderProductEntity.getProductEntity().getProductId());
        model.addAttribute("productName", orderProductEntity.getProductEntity().getProductName());
        model.addAttribute("orderId", orderProductEntity.getOrderEntity().getOrderId());
        return "/reviewPages/save";
    }
    //상품 후기 저장
    @PostMapping("/save")
    public String save(@ModelAttribute ReviewDTO reviewDTO, @RequestParam Long productId, @RequestParam Long memberId,
                       @RequestParam Long orderId){
        reviewService.save(reviewDTO, memberId, productId, orderId);
        return "redirect:/review/findByMemberId/" + memberId;
    }

    //나의 작성 후기 보기
    @GetMapping("/findByMemberId/{memberId}")
    public String findByMemberId(@PathVariable Long memberId, Model model){
        List<ReviewDTO> reviewDTOList = reviewService.findByMemberId(memberId);
        model.addAttribute("reviewList", reviewDTOList);
        return "/reviewPages/list";
    }

    //후기 수정 form 보여주기
    @GetMapping("/update/{reviewId}")
    public String updateForm(@PathVariable Long reviewId, Model model){
        ReviewDTO reviewDTO = reviewService.findById(reviewId);
        model.addAttribute("review", reviewDTO);
        return "/reviewPages/update";
    }

    //후기 수정하기
    @PostMapping("/update")
    public String update(@ModelAttribute ReviewDTO reviewDTO,@RequestParam Long productId, @RequestParam Long memberId, HttpSession session){
        reviewService.update(reviewDTO, productId);
        return "redirect:/review/findByMemberId/" + memberId;
    }

    //후기 삭제
    @GetMapping("/delete/{reviewId}")
    public String reviewDelete(@PathVariable Long reviewId,@RequestParam Long productId, HttpSession session){
        reviewService.deleteById(reviewId, productId);
        Long memberId = (Long) session.getAttribute("loginId");
        return "redirect:/review/findByMemberId/" + memberId;
    }


}
