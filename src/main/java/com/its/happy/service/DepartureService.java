package com.its.happy.service;

import com.its.happy.dto.DepartureDTO;
import com.its.happy.entity.DepartureEntity;
import com.its.happy.entity.MemberEntity;
import com.its.happy.repository.DepartureRepository;
import com.its.happy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartureService {
    private final DepartureRepository departureRepository;
    private final MemberRepository memberRepository;

    public Long save(DepartureDTO departureDTO, Long loginId) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(loginId);
        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            return departureRepository.save(DepartureEntity.toEntity(departureDTO, memberEntity)).getDepartureId();
        }
        return null;
    }

    public DepartureDTO findById(Long departureId) {
        Optional<DepartureEntity> optionalDepartureEntity = departureRepository.findById(departureId);
        if(optionalDepartureEntity.isPresent()) {
            DepartureEntity departureEntity = optionalDepartureEntity.get();
            return DepartureDTO.toDTO(departureEntity);
        }
        return null;
    }
}
