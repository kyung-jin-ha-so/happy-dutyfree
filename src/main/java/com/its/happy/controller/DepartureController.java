package com.its.happy.controller;

import com.its.happy.dto.DepartureDTO;
import com.its.happy.dto.PassportDTO;
import com.its.happy.service.DepartureService;
import com.its.happy.service.PassportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/departure")
@RequiredArgsConstructor
public class DepartureController {
    private final DepartureService departureService;

    @GetMapping("/save-form")
    public String saveForm() {
        return "/departurePages/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute DepartureDTO departureDTO, HttpSession session) {
        Long loginId = (Long) session.getAttribute("loginId");
        departureService.save(departureDTO, loginId);
        return "redirect:/member/mypage";
    }
}
