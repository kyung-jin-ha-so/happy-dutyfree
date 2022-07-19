package com.its.happy.service;

import com.its.happy.dto.MemberDTO;
import com.its.happy.entity.MemberEntity;
import com.its.happy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원가입 구현
    public void save(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toSave(memberDTO);
        System.out.println("MemberService.save");
        System.out.println("memberDTO = " + memberDTO);
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
        if(optionalMemberEntity.isPresent()){
            MemberEntity loginEntity = optionalMemberEntity.get();
            if(loginEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
                return MemberDTO.toMemberDTO(loginEntity);
            }else {
                return null; //비밀번호 불일치시 null로 리턴
            }
        } else {
            return null; //해당 계정이 없음시 null로 리턴
        }
    }


}
