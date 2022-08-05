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



    // 이메일 중복체크
    public String emailDuplicateCheck(String memberEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        if (optionalMemberEntity.isEmpty()) {
            return "OK";
        } else {
            return "NO";
        }
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

    // 카카오톡 로그인
    public String getAccessToken(String authorize_code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //    POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //    POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id="+"a88489884189e052d191c60987e50cab");
            sb.append("&redirect_uri=http://localhost:8080/memeber/kakaoLogin");
            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();

            //    결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //    Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return access_Token;
    }


}


