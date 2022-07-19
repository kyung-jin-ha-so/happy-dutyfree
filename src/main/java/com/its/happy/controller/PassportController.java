package com.its.happy.controller;

import com.its.happy.dto.PassportDTO;
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
@RequestMapping("/passport")
@RequiredArgsConstructor
public class PassportController {

    private final PassportService passportService;

    @GetMapping("/save-form")
    public String saveForm(Model model, HttpSession session) {
        Long loginId = (Long) session.getAttribute("loginId");
        PassportDTO passportDTO = passportService.findByLoginId(loginId);
        model.addAttribute("passport", passportDTO);
        return "/passportPages/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute PassportDTO passportDTO, HttpSession session) {
        Long loginId = (Long) session.getAttribute("loginId");
        passportService.save(passportDTO, loginId);
        return "redirect:/member/mypage";
    }
}
