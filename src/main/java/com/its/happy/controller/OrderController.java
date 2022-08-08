package com.its.happy.controller;

import com.its.happy.dto.*;
import com.its.happy.entity.CartEntity;
import com.its.happy.repository.CartRepository;
import com.its.happy.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final CartRepository cartRepository;
    private final CouponService couponService;
    private final PointService pointService;
    private final CartService cartService;
    private final ProductService productService;
    private final OrderProductService orderProductService;
    private final OrderDepartureService orderDepartureService;

    @GetMapping("/{memberId}")
    public String orderTest(@PathVariable Long memberId, CartArrayDTO cartArrayDTO) {
        System.out.println("memberId = " + memberId);
        System.out.println("cartArrayDTO = " + cartArrayDTO);
        return "index";
    }

    // 장바구니 구매
    @GetMapping("/save-form")
    public String saveForm(@ModelAttribute CartArrayDTO cartArrayDTO, Model model, HttpSession session) {
        System.out.println("OrderController.saveForm");

        // 장바구니에서 상품 array 옮겨담기
        List<CartDTO> cartDTOList = new ArrayList<>();
        for (int i = 0; i < cartArrayDTO.getCarts().size(); i++) {
            CartDTO cartDTO = cartArrayDTO.getCarts().get(i);
            System.out.println("cartDTO = " + cartDTO);
            cartDTOList.add(cartDTO);
        }
        session.setAttribute("cartDTOList", cartDTOList); // save 메서드에서 쓰기 위해 session
        model.addAttribute("cartList", cartDTOList);

        // loginId(member) model
        Long loginId = (Long) session.getAttribute("loginId");
        MemberDTO memberDTO = orderService.findByMemberId(loginId);
        model.addAttribute("member", memberDTO);

        return "/orderPages/save";
    }

    // 바로 구매
//    @GetMapping("/save-form2")
//    public String saveForm(Model model, HttpSession session, @RequestParam("productId") Long productId, @RequestParam("orderQty") int orderQty) {
//
//        System.out.println("cartDTOList = " + cartDTOList);
//        model.addAttribute("cartList", cartDTOList);
//        Long loginId = (Long) session.getAttribute("loginId");
//        MemberDTO memberDTO = orderService.findByMemberId(loginId);
//        model.addAttribute("member", memberDTO);
//        return "/orderPages/save";
//    }

    @PostMapping("/save")
    public ResponseEntity save(@ModelAttribute OrderDTO orderDTO, @RequestParam("orderDepartureAirport") String orderDepartureAirport, @RequestParam("orderDepartureDate") String orderDepartureDate, @RequestParam("orderDepartureNumber") String orderDepartureNumber, HttpSession session) {
        System.out.println("OrderController.save");
        System.out.println("orderDTO = " + orderDTO + ", orderDepartureAirport = " + orderDepartureAirport + ", orderDepartureDate = " + orderDepartureDate + ", orderDepartureNumber = " + orderDepartureNumber + ", session = " + session);

        Long memberId = orderDTO.getMemberId();
        Long couponMemberId = orderDTO.getCouponMemberId();
        // 쿠폰 사용 처리
        CouponMemberDTO couponMemberDTO = couponService.findByCouponMemberId(couponMemberId);
        couponMemberDTO.setCouponStatus("사용 후");
        couponService.updateCouponMember(couponMemberDTO);

        // 적립금 사용 처리
        PointDTO pointDTO = new PointDTO();
        pointDTO.setPointValue(-orderDTO.getPointUseValue());
        pointDTO.setMemberId(memberId);
        pointService.update(memberId, pointDTO);

        // 주문 저장하기
        orderDTO.setOrderStatus("주문완료");
        Long orderId = orderService.save(orderDTO);

        // 장바구니 삭제, 주문수량 빼기, orderProduct DB 저장
        List<CartDTO> cartDTOList = (List<CartDTO>) session.getAttribute("cartDTOList");
        session.removeAttribute("cartDTOList");
        for (int i = 0; i < cartDTOList.size(); i++) {
            cartService.deleteById(cartDTOList.get(i).getCartId()); // 장바구니 삭제
            ProductDTO productDTO = productService.findById(cartDTOList.get(i).getProductId()); // DTO 가져오기
            productDTO.setProductQuantity(productDTO.getProductQuantity() - cartDTOList.get(i).getCartQty()); // 주문수량 빼기
            productService.updateQty(productDTO); // 수정된 DTO 업데이트
            // orderProduct DB 저장
            OrderProductDTO orderProductDTO = new OrderProductDTO();
            orderProductDTO.setOrderQty(cartDTOList.get(i).getCartQty());
            orderProductDTO.setProductDiscount(cartDTOList.get(i).getProductDiscount());
            orderProductDTO.setProductOriginalPrice(cartDTOList.get(i).getProductOriginalPrice());
            orderProductDTO.setProductId(cartDTOList.get(i).getProductId());
            orderProductDTO.setOrderId(orderId);
            orderProductService.save(orderProductDTO);
        }

        // 주문출국 정보 저장하기
        OrderDepartureDTO orderDepartureDTO = new OrderDepartureDTO();
        orderDepartureDTO.setOrderDepartureAirport(orderDepartureAirport);
        orderDepartureDTO.setOrderDepartureDate(orderDepartureDate);
        orderDepartureDTO.setOrderDepartureNumber(orderDepartureNumber);
        orderDepartureDTO.setMemberId(memberId);
        orderDepartureDTO.setOrderId(orderId);
        orderDepartureService.save(orderDepartureDTO);

        if (orderId != null)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/test")
    public String saveTest(Model model, HttpSession session) {
        List<CartDTO> cartDTOList = new ArrayList<>();

        Optional<CartEntity> optionalCartEntity = cartRepository.findById(1L);
        CartEntity cartEntity = optionalCartEntity.get();
        CartDTO cartDTO = CartDTO.toCartDTO(cartEntity);
        cartDTOList.add(cartDTO);

        Optional<CartEntity> optionalCartEntity2 = cartRepository.findById(2L);
        CartEntity cartEntity2 = optionalCartEntity2.get();
        CartDTO cartDTO2 = CartDTO.toCartDTO(cartEntity2);
        cartDTOList.add(cartDTO2);

        session.setAttribute("cartDTOList", cartDTOList);
        model.addAttribute("cartList", cartDTOList);

        // loginId(member) model
        Long loginId = (Long) session.getAttribute("loginId");
        MemberDTO memberDTO = orderService.findByMemberId(loginId);
        model.addAttribute("member", memberDTO);

        //
        ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO(1L, 1298.91, "2022-07-29");
        model.addAttribute("exRate", exchangeRateDTO);
        return "/orderPages/test";
    }

}
