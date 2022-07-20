package com.its.happy.controller;

import com.its.happy.dto.MemberDTO;
import com.its.happy.service.MemberService;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
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

//    //핸드폰 문자 인증
//    @PostMapping("/mobileCheck")
//    public @ResponseBody String mobilCheck(@RequestParam String memberMobile) throws CoolsmsException){
//        String api_key = "NCSHONZYXFYSSMEB";
//        String api_secret = "UNNGPMH4FUXIRCPQY2VSJPZFOZTB80QE";
//        Message coolsms = new Message(api_key, api_secret);
//
//        Random rand  = new Random();
//        String numStr = "";
//        for(int i=0; i<4; i++) {
//            String ran = Integer.toString(rand.nextInt(10));
//            numStr+=ran;
//        }
//
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("to", "01072248086");    // 수신전화번호 (ajax로 view 화면에서 받아온 값으로 넘김)
//        params.put("from", "01072248086");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
//        params.put("type", "sms");
//        params.put("text", "인증번호는 [" + numStr + "] 입니다.");
//
//        coolsms.send(params); // 메시지 전송
//
//        return numStr;
//    }



}
