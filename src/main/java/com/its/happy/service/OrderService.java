package com.its.happy.service;

import com.its.happy.dto.MemberDTO;
import com.its.happy.entity.MemberEntity;
import com.its.happy.repository.MemberRepository;
import com.its.happy.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;


    public MemberDTO findByMemberId(Long memberId) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
        if(optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            return MemberDTO.toMemberDTO(memberEntity);
        }
        return null;
    }
}
