package com.its.happy.controller;

import com.its.happy.dto.MemberDTO;
import com.its.happy.dto.PassportDTO;
import com.its.happy.service.MemberService;
import com.its.happy.service.PassportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/passport")
@RequiredArgsConstructor
public class PassportController {

    private final PassportService passportService;
    private final MemberService memberService;

    @GetMapping("/save-form")
    public String saveForm(Model model, HttpSession session) {
        Long loginId = (Long) session.getAttribute("loginId");
        PassportDTO passportDTO = passportService.findByLoginId(loginId);
        MemberDTO memberDTO = memberService.findById(loginId);
        model.addAttribute("member", memberDTO);
        if(passportDTO == null) {
            System.out.println("null");
            return "/passportPages/save";
        } else {
            System.out.println("not null");
            model.addAttribute("passport", passportDTO);
            return "passportPages/update";
        }
    }

    @PostMapping("/save")
    public String save(@ModelAttribute PassportDTO passportDTO, HttpSession session) {
        Long loginId = (Long) session.getAttribute("loginId");
        passportService.save(passportDTO, loginId);
        return "redirect:/myPageMain";
    }

    @PostMapping("/findByLoginId")
    public @ResponseBody String findByLoginId(@RequestParam Long loginId){
        PassportDTO passportDTO = passportService.findByLoginId(loginId);
        if(passportDTO != null){
            return "ok";
        }else {
            return "no";
        }
    }
}
