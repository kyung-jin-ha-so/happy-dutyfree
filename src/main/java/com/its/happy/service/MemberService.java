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

    public void save(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toSave(memberDTO);
        System.out.println("MemberService.save");
        System.out.println("memberDTO = " + memberDTO);
        memberRepository.save(memberEntity);
    }

    public String duplicateCheck(String memberEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        if(optionalMemberEntity.isEmpty()){
            return "OK";
        }else {
            return "NO";
        }
    }
}
