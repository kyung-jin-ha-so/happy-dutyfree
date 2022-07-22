package com.its.happy.service;

import com.its.happy.dto.MemberDTO;
import com.its.happy.entity.MemberEntity;
import com.its.happy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    // 회원가입 구현 - 비밀번호 암호화
    @Transactional
    public Long save(MemberDTO memberDTO) {
        String password = memberDTO.getMemberPassword();
        String encodedPassword = passwordEncoder.encode(password);
        memberDTO.setMemberPassword(encodedPassword);
        MemberEntity memberEntity = MemberEntity.toSave(memberDTO);
        return memberRepository.save(memberEntity).getMemberId();
    }



    // 이메일 중복체크
    public String duplicateCheck(String memberEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        if (optionalMemberEntity.isEmpty()) {
            return "OK";
        } else {
            return "NO";
        }
    }

    // 로그인 구현
    public MemberDTO login(MemberDTO memberDTO) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if (optionalMemberEntity.isPresent()) {
            MemberEntity loginEntity = optionalMemberEntity.get();
//            if(loginEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
            if (passwordEncoder.matches(memberDTO.getMemberPassword(), loginEntity.getMemberPassword())) {
                return MemberDTO.toMemberDTO(loginEntity);
            } else {
                return null; //비밀번호 불일치시 null 리턴
            }
        } else {
            return null; //해당 계정이 없음시 null 리턴
        }
    }

    //핸드폰 문자 인증
    public String sendSMS(String memberMobile) throws CoolsmsException {
        String api_key = "NCSYFPXKKTOS0NSJ";
        String api_secret = "HLNOZ8UXWGTAVI4CT8GONJCDTPEWYAFD";
        Message coolsms = new Message(api_key, api_secret);

        Random rand  = new Random();
        String numStr = "";
        for(int i=0; i<4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr+=ran;
        }

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", memberMobile);
        params.put("from", "01072248086");
        params.put("type", "SMS");
        params.put("text", "해피면세점 인증번호는 ["+numStr+"] 입니다");

        coolsms.send(params); // 메시지 전송

        return numStr;
    }


    // 아이디찾기 - 핸드폰번호로 일치하는 회원 찾기
    public String mobileCheck(String memberMobile) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberMobile(memberMobile);
        if(optionalMemberEntity.isEmpty()){
            return "NO";
        }else {
            return "OK";
        }
    }


    public MemberDTO findEmail(String memberMobile) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberMobile(memberMobile);
        if(optionalMemberEntity.isPresent()){
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        }else {
            return null;
        }
    }
}


