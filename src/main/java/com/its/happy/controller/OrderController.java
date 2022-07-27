package com.its.happy.controller;

import com.its.happy.dto.CartArrayDTO;
import com.its.happy.dto.CartDTO;
import com.its.happy.dto.MemberDTO;
import com.its.happy.dto.OrderDTO;
import com.its.happy.service.MemberService;
import com.its.happy.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;

    @GetMapping("/{memberId}")
    public String orderTest(@PathVariable Long memberId, CartArrayDTO cartArrayDTO){
        System.out.println("memberId = " + memberId);
        System.out.println("cartArrayDTO = " + cartArrayDTO);
        return "index";
    }

    @GetMapping("/save-form")
    public String saveForm(@ModelAttribute CartArrayDTO cartArrayDTO, Model model, HttpSession session, @RequestParam("productId") Long productId, @RequestParam("orderQty") int orderQty) {
        System.out.println("OrderController.saveForm");
        System.out.println("cartArrayDTO = " + cartArrayDTO +  ", productId = " + productId + ", orderQty = " + orderQty);
        List<CartDTO> cartDTOList = new ArrayList<>();
        for (int i = 0; i < cartArrayDTO.getCarts().size(); i++) {
            CartDTO cartDTO = cartArrayDTO.getCarts().get(i);
            System.out.println("cartDTO = " + cartDTO);
            cartDTOList.add(cartDTO);
        }
        System.out.println("cartDTOList = " + cartDTOList);
        model.addAttribute("cartList", cartDTOList);
        Long loginId = (Long) session.getAttribute("loginId");
        MemberDTO memberDTO = orderService.findByMemberId(loginId);
        model.addAttribute("member", memberDTO);
//        return null;
        return "/orderPages/save";
    }

    @GetMapping("/test")
    public String iamportTest() {
        return "/orderPages/test";
    }

}
