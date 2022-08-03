package com.its.happy.service;

import com.its.happy.dto.CouponMemberDTO;
import com.its.happy.dto.MemberDTO;
import com.its.happy.dto.PointDTO;
import com.its.happy.entity.CouponMemberEntity;
import com.its.happy.entity.MemberEntity;
import com.its.happy.entity.PointEntity;
import com.its.happy.repository.MemberRepository;
import com.its.happy.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
            PointDTO pointDTO = new PointDTO();
            pointDTO.setPointValue(1000000);
            PointEntity pointEntity = PointEntity.toSave(memberEntity,pointDTO);
            pointRepository.save(pointEntity);
        }
    }


    //적립금 사용 save
    public void update(Long savedId, PointDTO pointDTO) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(savedId);
        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            PointEntity pointEntity = PointEntity.toSave(memberEntity,pointDTO);
            pointRepository.save(pointEntity);
        }
    }


    // 포인트 전체조회 (로그인한 회원 기준)
    public List<PointDTO> findByPoint(Long memberId){
        List<PointEntity> pointEntityList = pointRepository.findByMemberEntity_MemberId(memberId);
        List<PointDTO> pointDTOList = new ArrayList<>();
        for(PointEntity pointEntity : pointEntityList){
            pointDTOList.add(PointDTO.toPointDTO(pointEntity));
        }
        return pointDTOList;
    }







}
