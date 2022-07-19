package com.its.happy.controller;

import com.its.happy.dto.MemberDTO;
import com.its.happy.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        System.out.println("memberDTO = " + memberDTO);
        memberService.save(memberDTO);
        return "index";
    }

    // 이메일 중복체크
    @PostMapping("/duplicate-check")
    public @ResponseBody String duplicateCheck(@RequestParam String memberEmail){
        String emailResult = memberService.duplicateCheck(memberEmail);
        return emailResult;
    }


}
