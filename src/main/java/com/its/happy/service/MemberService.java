package com.its.happy.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.its.happy.dto.MemberDTO;
import com.its.happy.entity.MemberEntity;
import com.its.happy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

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

    // 카카오 로그인시 회원가입으로 이동
    public MemberDTO kakaoLogin(String membeerKakaoId) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberKakaoId(membeerKakaoId);
        if (optionalMemberEntity.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    // 이메일 중복체크
    public String emailDuplicateCheck(String memberEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        if (optionalMemberEntity.isEmpty()) {
            return "OK";
        } else {
            return "NO";
        }
    }


    //핸드폰번호 중복체크
    public String mobileDuplicateCheck(String memberMobile) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberMobile(memberMobile);
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

        Random rand = new Random();
        String numStr = "";
        for (int i = 0; i < 4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr += ran;
        }

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", memberMobile);
        params.put("from", "01072248086");
        params.put("type", "SMS");
        params.put("text", "행복면세점 인증번호는 [" + numStr + "] 입니다");

        coolsms.send(params); // 메시지 전송

        return numStr;
    }


    // 아이디찾기 - 핸드폰번호로 일치하는 회원 찾기
    public String mobileCheck(String memberMobile) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberMobile(memberMobile);
        if (optionalMemberEntity.isEmpty()) {
            return "NO";
        } else {
            return "OK";
        }
    }


    // 일치한 핸드폰번호로 해당회원 이메일 찾기
    public MemberDTO findEmail(String memberMobile) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberMobile(memberMobile);
        if (optionalMemberEntity.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    // 개인정보 상세조회
    public MemberDTO findById(Long memberId) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
        if (optionalMemberEntity.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }


    public void update(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.toUpdate(memberDTO));
    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberEntity memberEntity : memberEntityList) {
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
        }
        return memberDTOList;
    }


    // 회원삭제
    public void deleteById(Long memberId) {
        memberRepository.deleteById(memberId);
    }

    public String passwordCk(String memberPassword,Long memberId) {
        System.out.println("멤버서비스 실행");
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
        if(optionalMemberEntity.isPresent()){
            MemberEntity memberEntity = optionalMemberEntity.get();
            if(passwordEncoder.matches(memberPassword,memberEntity.getMemberPassword())){
                return "OK";
            }else{
                return "NO";
            }
        }
        return "NO";
    }

    public void passwordUpdate(MemberDTO memberDTO) {
        String password = memberDTO.getMemberPassword();
        String encodedPassword = passwordEncoder.encode(password);
        memberDTO.setMemberPassword(encodedPassword);
        memberRepository.save(MemberEntity.toUpdate(memberDTO));
    }

    // 비밀번호 찾기 시 이메일 확인
    public MemberDTO emailCheck(String memberEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        if (optionalMemberEntity.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    // 비밀번호찾기 - 멤버아이디랑 해당 멤버아이디의 핸드폰번호가 일치하는지 확인
    public String emailMobileCheck(String memberId, String memberMobile) {
        //스트링으로 받은 멤버아이디 형변환
        Long longMemberId = Long.valueOf(memberId);
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberMobile(memberMobile);
        if(optionalMemberEntity.isPresent()){
            Long result = optionalMemberEntity.get().getMemberId();
            if(result == longMemberId){
                return "OK";
            }else {
                return "NO";
            }
        }else {
            return "NO";
        }
    }
}


