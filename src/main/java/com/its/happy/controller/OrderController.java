package com.its.happy.controller;

import com.its.happy.dto.*;
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

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final CouponService couponService;
    private final PointService pointService;
    private final CartService cartService;
    private final ProductService productService;
    private final OrderProductService orderProductService;
    private final OrderDepartureService orderDepartureService;
    private final MemberService memberService;
    private final ExchangeRateService exchangeRateService;

    // 장바구니 구매
    @GetMapping("/save-form")
    public String saveForm(@ModelAttribute CartArrayDTO cartArrayDTO, Model model, HttpSession session) {
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
        MemberDTO memberDTO = memberService.findById(loginId);
        model.addAttribute("member", memberDTO);

        return "/orderPages/save";
    }

    // 바로 구매
    @GetMapping("/save-form2")
    public String saveForm2(Model model, HttpSession session, @RequestParam("productId") Long productId, @RequestParam("orderQty") int orderQty) {
        System.out.println("OrderController.saveForm2");
        // loginId(member) model
        Long loginId = (Long) session.getAttribute("loginId");
        MemberDTO memberDTO = memberService.findById(loginId);
        model.addAttribute("member", memberDTO);

        // html과 save메서드를 두 개 만들지 않기 위한 작업
        // 상품 정보를 장바구니에 담는 과정
        CartDTO cartDTO = new CartDTO();
        cartDTO.setProductId(productId);
        cartDTO.setCartQty(orderQty);
        cartDTO.setMemberId(loginId);
        CartDTO findCartDTO = cartService.save2(cartDTO);

        // 리턴받은 findCartDTO의 필드를 productDTO의 정보로 채움
        ProductDTO productDTO = productService.findById(productId);
        findCartDTO.setProductName(productDTO.getProductName());
        findCartDTO.setProductOriginalPrice(productDTO.getProductOriginalPrice());
        findCartDTO.setProductDiscount(productDTO.getProductDiscount());
        findCartDTO.setProductPrice(productDTO.getProductPrice());
        findCartDTO.setProductThumbnail(productDTO.getProductThumbnail());
        System.out.println("productDTO = " + productDTO);

        // 만든 DTO를 리스트에 담고 session, model
        List<CartDTO> cartDTOList = new ArrayList<>();
        cartDTOList.add(findCartDTO);
        session.setAttribute("cartDTOList", cartDTOList); // save 메서드에서 쓰기 위해 session
        model.addAttribute("cartList", cartDTOList);

        return "/orderPages/save";
    }

    @PostMapping("/save")
    public ResponseEntity save(@ModelAttribute OrderDTO orderDTO, @RequestParam("orderDepartureAirport") String orderDepartureAirport, @RequestParam("orderDepartureDate") String orderDepartureDate, @RequestParam("orderDepartureNumber") String orderDepartureNumber, HttpSession session) {
        System.out.println("OrderController.save");
        System.out.println("orderDTO = " + orderDTO + ", orderDepartureAirport = " + orderDepartureAirport + ", orderDepartureDate = " + orderDepartureDate + ", orderDepartureNumber = " + orderDepartureNumber + ", session = " + session);

        Long memberId = orderDTO.getMemberId();
        System.out.println("memberId = " + memberId);

        // 쿠폰 사용 처리
        if (orderDTO.getCouponMemberId() != null) {
            Long couponMemberId = orderDTO.getCouponMemberId();
            System.out.println("couponMemberId = " + couponMemberId);
            CouponMemberDTO couponMemberDTO = couponService.findByCouponMemberId(couponMemberId);
            couponMemberDTO.setCouponStatus("사용 후");
            couponService.updateCouponMember(couponMemberDTO);
            System.out.println("couponMemberDTO = " + couponMemberDTO);
        }

        // 적립금 사용 처리
        PointDTO pointDTO = new PointDTO();
        pointDTO.setPointValue(-orderDTO.getPointUseValue());
        pointDTO.setMemberId(memberId);
        pointService.update(memberId, pointDTO);
        System.out.println("pointDTO = " + pointDTO);

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
            orderProductDTO.setProductPrice(cartDTOList.get(i).getProductPrice());
            orderProductDTO.setProductName(cartDTOList.get(i).getProductName());
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

    @GetMapping("/list")
    public String list(Model model, HttpSession session) {
        Long loginId = (Long) session.getAttribute("loginId");
        List<OrderDTO> orderDTOList = orderService.findByMemberId(loginId);
        model.addAttribute("orderList", orderDTOList);
        System.out.println("orderDTOList = " + orderDTOList);
        return "/orderPages/list";
    }

    @GetMapping("/detail/{orderId}")
    public String detail(@PathVariable Long orderId, Model model) {
        System.out.println("OrderController.detail");
        System.out.println("orderId = " + orderId);
        OrderDTO orderDTO = orderService.findById(orderId);
        model.addAttribute("order", orderDTO);

        return "/orderPages/detail";
    }

    @GetMapping("/cancel/{orderId}")
    public String cancel(@PathVariable Long orderId) {
        System.out.println("OrderController.cancel");
        System.out.println("orderId = " + orderId);
        OrderDTO orderDTO = orderService.findById(orderId);
        orderDTO.setOrderStatus("주문취소");
        orderService.save(orderDTO);
        return "redirect:/order/list";
    }

    @GetMapping("/re-order")
    public String reOrder(@ModelAttribute CartArrayDTO cartArrayDTO, Model model, HttpSession session) {
        System.out.println("OrderController.reOrder");
        List<CartDTO> cartDTOList = new ArrayList<>();
        for (int i = 0; i < cartArrayDTO.getCarts().size(); i++) {
            CartDTO cartDTO1 = cartArrayDTO.getCarts().get(i);
            System.out.println("cartDTO1 = " + cartDTO1);
            CartDTO cartDTO = cartService.save2(cartDTO1);
            cartDTOList.add(cartDTO);
            System.out.println("OrderController.reOrder");
        }

        Long memberId = (Long) session.getAttribute("loginId");

        model.addAttribute("cartList", cartDTOList);

        List<PointDTO> pointDTOList = pointService.findByPoint(memberId);
        model.addAttribute("pointList",pointDTOList);

        List<CouponMemberDTO> couponMemberDTOList  = couponService.findByMyCoupon(memberId);
        model.addAttribute("myCoupon", couponMemberDTOList);

        ExchangeRateDTO exchangeRateDTO = exchangeRateService.findByDate();
        model.addAttribute("exchangeRateDTO", exchangeRateDTO);

        MemberDTO memberDTO = memberService.findById(memberId);
        model.addAttribute("member",memberDTO);

        return "cartPages/list";
    }

}
