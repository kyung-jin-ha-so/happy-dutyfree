package com.its.happy.controller;

import com.its.happy.dto.CartDTO;
import com.its.happy.dto.LikeDTO;
import com.its.happy.service.CartService;
import com.its.happy.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final ProductService productService;

    @GetMapping("/save")
    public ResponseEntity save(@RequestParam("productId") Long productId, @RequestParam("memberId") Long memberId, @RequestParam("cartQty") int cartQty) {
        String result = cartService.save(productId, memberId, cartQty);
        System.out.println("result = " + result);
        if (result == "ok") {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/addToCart")
    public ResponseEntity update(@RequestParam("productId") Long productId, @RequestParam("memberId") Long memberId, @RequestParam("cartQty") int cartQty){
        System.out.println("productId = " + productId);
        cartService.update(productId, memberId, cartQty);
        System.out.println("memberId = " + memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/cartList")
    public String cartList(HttpSession session, Model model, @ModelAttribute LikeDTO likeDTO){
        Long memberId = (Long) session.getAttribute("loginId");
        List<CartDTO> cartDTOList = cartService.findById(memberId);
        model.addAttribute("cartList", cartDTOList);
        return "cartPages/list";
    }
    @PostMapping("/update/")
    public ResponseEntity updateCartQty(@RequestBody CartDTO cartDTO){
        cartService.updateCartQty(cartDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/delete/{cartId}")
    public String deleteById(@PathVariable Long cartId){
        cartService.deleteById(cartId);
        return "redirect:/cart/cartList";
    }
}

