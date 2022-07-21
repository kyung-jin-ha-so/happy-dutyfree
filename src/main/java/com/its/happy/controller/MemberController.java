package com.its.happy.controller;

import com.its.happy.dto.MemberDTO;
import com.its.happy.service.MemberService;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Random;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    // 회원가입 페이지 이동
    @GetMapping("/save")
    public String saveForm(){
        return "/memberPages/save";
    }

    // 회원가입 구현
    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO){
        memberService.save(memberDTO);
        return "/memberPages/login";
    }

    // 이메일 중복체크
    @PostMapping("/duplicate-check")
    public @ResponseBody String duplicateCheck(@RequestParam String memberEmail){
        String emailResult = memberService.duplicateCheck(memberEmail);
        return emailResult;
    }

    // 로그인 페이지 이동
    @GetMapping("/login")
    public String loginForm(){
        return "/memberPages/login";
    }

    // 로그인 구현
    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session){
        MemberDTO loginResult = memberService.login(memberDTO);
        if(loginResult != null){
            session.setAttribute("loginEmail",loginResult.getMemberEmail());
            session.setAttribute("loginId",loginResult.getMemberId());
            return "index";
        } else {
            return "/memberPages/login";
        }
    }

    // 로그아웃 구현
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

    // 핸드폰 문자 인증
    @GetMapping("/sendSMS")
    public @ResponseBody String sendSMS(@RequestParam String memberMobile) throws CoolsmsException {
        return memberService.sendSMS(memberMobile);
    }

}
