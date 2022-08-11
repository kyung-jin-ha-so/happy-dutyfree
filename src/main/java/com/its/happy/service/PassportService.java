package com.its.happy.service;

import com.its.happy.dto.PassportDTO;
import com.its.happy.entity.MemberEntity;
import com.its.happy.entity.PassportEntity;
import com.its.happy.repository.MemberRepository;
import com.its.happy.repository.PassportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PassportService {
    private final PassportRepository passportRepository;
    private final MemberRepository memberRepository;

    public void save(PassportDTO passportDTO, Long loginId) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(loginId);
        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            passportRepository.save(PassportEntity.toEntity(passportDTO, memberEntity));
        }
    }

    public PassportDTO findByLoginId(Long loginId) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(loginId);
        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            Optional<PassportEntity> optionalPassportEntity = passportRepository.findByMemberEntity(memberEntity);
            if (optionalPassportEntity.isPresent()) {
                PassportEntity passportEntity = optionalPassportEntity.get();
                PassportDTO passportDTO = PassportDTO.toDTO(passportEntity);
                return passportDTO;
            }
        }
        return null;
    }

}
