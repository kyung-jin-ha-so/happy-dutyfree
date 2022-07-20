package com.its.happy.service;

import com.its.happy.dto.MemberDTO;
import com.its.happy.entity.MemberEntity;
import com.its.happy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    // 회원가입 구현 - 비밀번호 암호화
    @Transactional
    public void save(MemberDTO memberDTO) {
        String password = memberDTO.getMemberPassword();
        String encodedPassword = passwordEncoder.encode(password);
        memberDTO.setMemberPassword(encodedPassword);
        MemberEntity memberEntity = MemberEntity.toSave(memberDTO);
        memberRepository.save(memberEntity);
    }


    // 이메일 중복체크
    public String duplicateCheck(String memberEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        if(optionalMemberEntity.isEmpty()){
            return "OK";
        }else {
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
                return null; //비밀번호 불일치시 null로 리턴
            }
        }else{
                return null; //해당 계정이 없음시 null로 리턴
            }
        }
    }


