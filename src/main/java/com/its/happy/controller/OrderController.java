package com.its.happy.controller;

import com.its.happy.dto.CartArrayDTO;
import com.its.happy.dto.CartDTO;
import com.its.happy.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/save-form")
    public String saveForm(@ModelAttribute CartArrayDTO cartArrayDTO, Model model, HttpSession session) {
        System.out.println("OrderController.saveForm");
        System.out.println("cartArrayDTO = " + cartArrayDTO);
        List<CartDTO> cartDTOList = new ArrayList<>();
        for (int i = 0; i < cartArrayDTO.getCarts().size(); i++) {
            CartDTO cartDTO = cartArrayDTO.getCarts().get(i);
            System.out.println("cartDTO = " + cartDTO);
            cartDTOList.add(cartDTO);
        }
        System.out.println("cartDTOList = " + cartDTOList);
        model.addAttribute("cartList", cartDTOList);

        Long loginId = (Long) session.getAttribute("loginId");
        return null;
//        return "/orderPages/save";
    }

}
