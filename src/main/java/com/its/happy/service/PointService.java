package com.its.happy.service;

import com.its.happy.dto.PointDTO;
import com.its.happy.entity.MemberEntity;
import com.its.happy.entity.PointEntity;
import com.its.happy.repository.MemberRepository;
import com.its.happy.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;
    private final MemberRepository memberRepository;

    // 회원가입시 적립금 100만원 save
    public void save(Long savedId) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(savedId);
        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            PointEntity pointEntity = PointEntity.toSave(memberEntity);
            pointRepository.save(pointEntity);
        }
    }


}
