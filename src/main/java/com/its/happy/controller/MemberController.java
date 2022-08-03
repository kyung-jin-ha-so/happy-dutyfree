package com.its.happy.controller;

import com.its.happy.dto.MemberDTO;
import com.its.happy.entity.MemberEntity;
import com.its.happy.service.MemberService;
import com.its.happy.service.PointService;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final PointService pointService;

    // 회원가입 페이지 이동
    @GetMapping("/save")
    public String saveForm(){
        return "/memberPages/save";
    }

    // 회원가입 구현
    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO){
        Long savedId = memberService.save(memberDTO);
        // 회원가입시 적립금 100만원 저장
        pointService.save(savedId);
        return "/memberPages/login";
    }

    // 이메일 중복체크
    @PostMapping("/email-duplicate-check")
    public @ResponseBody String emailDuplicateCheck(@RequestParam String memberEmail){
        String emailResult = memberService.emailDuplicateCheck(memberEmail);
        return emailResult;
    }

    // 핸드폰번호 중복체크
    @PostMapping("/mobile-duplicate-check")
    public @ResponseBody String mobileDuplicateCheck(@RequestParam String memberMobile){
        String mobileResult = memberService.mobileDuplicateCheck(memberMobile);
        return mobileResult;
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

    //아이디찾기 화면 이동
    @GetMapping("/findEmail")
    public String findEmailForm(){
        return "/memberPages/findEmail";
    }
    

    // 아이디찾기 - 핸드폰번호로 일치하는 회원 찾기
    @PostMapping("/mobile-check")
    public @ResponseBody String mobileCheck(@RequestParam String memberMobile){
        String mobileResult = memberService.mobileCheck(memberMobile);
        return mobileResult;
    }

    // 핸드폰 인증 완료시 해당 전화번호를 가지고 있는 이메일 보여주기
    @GetMapping("/findEmailResult")
    public String findEmail(@RequestParam String memberMobile, Model model){
        MemberDTO memberDTO = memberService.findEmail(memberMobile);
        model.addAttribute("member",memberDTO);
        return "memberPages/findEmailResult";
    }

    // 비밀번호 변경 화면 요청
    @GetMapping("/passwordUpdate")
    public String passwordUpdateForm(Model model,HttpSession session){
        Long memberId = (long) session.getAttribute("loginId");
        MemberDTO memberDTO = memberService.findById(memberId);
        model.addAttribute("member",memberDTO);
        return "/memberPages/passwordUpdate";
    }

    // 비밀번호 변경 처리
    @PostMapping("/passwordUpdate")
    public String passwordUpdate(@ModelAttribute MemberDTO memberDTO){
        memberService.passwordUpdate(memberDTO);
        return "redirect:/myPageMain";
    }

    //비밀번호찾기 화면 이동
    @GetMapping("/findPassword")
    public String findPasswordForm(){
        return "/memberPages/findPassword";
    }


    // 비밀번호변경을 위한 비밀번호 확인 화면 이동
    @GetMapping("/passwordCheck")
    public String passwordCheckForm(HttpSession session,Model model){
        Long memberId = (long) session.getAttribute("loginId");
        MemberDTO memberDTO = memberService.findById(memberId);
        model.addAttribute("member",memberDTO);
        return "/memberPages/pwCkPwUpdate";
    }

    // 회원정보 수정을 위한 비밀번호 확인 화면 이동
    @GetMapping("/passwordCheckDetail")
    public String passwordCheckDetailForm(HttpSession session,Model model){
        Long memberId = (long) session.getAttribute("loginId");
        MemberDTO memberDTO = memberService.findById(memberId);
        model.addAttribute("member",memberDTO);
        return "/memberPages/pwCkDetail";
    }

    // 회원탈퇴를 위한 비밀번호 확인 화면 이동
    @GetMapping("/passwordCheckDelete")
    public String passwordCheckDeleteForm(HttpSession session,Model model){
        Long memberId = (long) session.getAttribute("loginId");
        MemberDTO memberDTO = memberService.findById(memberId);
        model.addAttribute("member",memberDTO);
        return "/memberPages/pwCkDelete";
    }


    // 비밀번호 일치여부 확인
    @PostMapping("/passwordCheck")
    public @ResponseBody String passwordCheck(@RequestParam String memberPassword,@RequestParam Long memberId){
        String result = memberService.passwordCk(memberPassword,memberId);
        return result;
    }


    //개인정보 상세조회
    @GetMapping("/{memberId}")
    public String findById(@PathVariable Long memberId, Model model){
        MemberDTO memberDTO = memberService.findById(memberId);
        model.addAttribute("member",memberDTO);
        return "memberPages/detail";
    }

    // 수정화면 요청
    @GetMapping("/update")
    public String updateForm(HttpSession session,Model model){
        Long memberId = (Long) session.getAttribute("loginId");
        MemberDTO memberDTO = memberService.findById(memberId);
        model.addAttribute("member",memberDTO);
        return "memberPages/update";
    }

    // 회원정보 수정 구현
    @PostMapping("/update")
    public String update(@ModelAttribute MemberDTO memberDTO){
        memberService.update(memberDTO);
        return "redirect:/member/"+memberDTO.getMemberId();
    }

    // 회원탈퇴 페이지 이동
    @GetMapping("/deleteMyselfForm")
    public String deleteMyselfForm(){
        return "/memberPages/deleteMyself";
    }

    // 회원탈퇴 - 회원
    @GetMapping("/deleteMyself")
    public String deleteMyself(HttpSession session){
        Long memberId = (Long) session.getAttribute("loginId");
        memberService.deleteById(memberId);
        return "redirect:/";
    }


}
