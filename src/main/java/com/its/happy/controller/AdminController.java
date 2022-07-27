package com.its.happy.controller;

import com.its.happy.common.PagingConst;
import com.its.happy.dto.CouponDTO;
import com.its.happy.dto.EventDTO;
import com.its.happy.dto.MemberDTO;
import com.its.happy.dto.ProductDTO;
import com.its.happy.service.CouponService;
import com.its.happy.service.EventService;
import com.its.happy.service.MemberService;
import com.its.happy.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
public class AdminController {
    private final ProductService productService;
    private final CouponService couponService;
    private final EventService eventService;
    private final MemberService memberService;

    @GetMapping("productList/")
    public String productFindAll(@PageableDefault(page = 1) Pageable pageable, Model model){
        Page<ProductDTO> productList = productService.findAll(pageable);
        model.addAttribute("productList", productList);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < productList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : productList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("sort", "all");
        return "/adminPages/productList";
    }
    //쿠폰 리스트 조회
    @GetMapping("/couponList")
    public String couponFindAll(Model model){
        List<CouponDTO> couponDTOList = couponService.findAll();
        model.addAttribute("couponList", couponDTOList);
        System.out.println("CouponController.findAll");
        System.out.println("couponDTOList = " + couponDTOList);
        return "/adminPages/couponList";
    }
    //이벤트 페이징목록
    @GetMapping("/eventList")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model){
        Page<EventDTO> eventList = eventService.paging(pageable);
        model.addAttribute("eventList", eventList);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT)))-1) * PagingConst.BLOCK_LIMIT+1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < eventList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : eventList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        for (EventDTO e : eventList) {
            System.out.println("for문 동작");
            System.out.println(e);
        }
        return "/adminPages/eventList";
    }

    // 회원 전체목록 조회
    @GetMapping("/memberList")
    public String memberFindAll(Model model){
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList",memberDTOList);
        return "/adminPages/memberList";
    }

    //회원 삭제 - 관리자버전
    @GetMapping("/delete/{memberId}")
    public String deleteById(@PathVariable Long memberId){
        memberService.deleteById(memberId);
        return "redirect:/admin/memberList";
    }

}
