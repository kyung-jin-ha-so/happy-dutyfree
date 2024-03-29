package com.its.happy.controller;

import com.its.happy.dto.CouponMemberDTO;
import com.its.happy.dto.MemberDTO;
import com.its.happy.dto.PointDTO;
import com.its.happy.service.MemberService;
import com.its.happy.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/point")
public class PointController {

    final PointService pointService;
    final MemberService memberService;

    @GetMapping("/findByMemberId")
    public String pointFindAll(HttpSession session,Model model){
        Long memberId = (Long) session.getAttribute("loginId");
        List<PointDTO> pointDTOList = pointService.findByPoint(memberId);
        model.addAttribute("pointList",pointDTOList);
        MemberDTO memberDTO = memberService.findById(memberId);
        model.addAttribute("member",memberDTO);
        return "memberPages/pointList";
    }






}
