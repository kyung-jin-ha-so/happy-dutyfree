package com.its.happy;

import com.its.happy.controller.PassportController;
import com.its.happy.dto.PassportDTO;
import com.its.happy.entity.MemberEntity;
import com.its.happy.repository.MemberRepository;
import com.its.happy.service.PassportService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PassportTest {
    @Autowired
    private PassportService passportService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("여권정보 저장 테스트")
    @Transactional
    @Rollback(value = false)
    public void passportSaveTest() {
        PassportDTO passportDTO = new PassportDTO("M123456", "GILDONG HONG", "2025-05-01");
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail("abc123@gmail.com");
        memberEntity.setMemberPassword("1234");
        memberEntity.setMemberName("홍길동");
        memberEntity.setMemberMobile("010-1234-1234");
        memberEntity.setMemberTier("브론즈");
        memberRepository.save(memberEntity);
        Long loginId = 1L;
        passportService.save(passportDTO, loginId);
        PassportDTO byLoginId = passportService.findByLoginId(loginId);
        assertThat(passportDTO.equals(byLoginId));
    }

    @Test
    @DisplayName("여권정보 저장 테스트2")
    @Transactional
    @Rollback(value = false)
    public void passportSaveTest2() {
        PassportDTO passportDTO = new PassportDTO("M123456", "GILDONG HONG", "2025-05-01");
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(1L);
        MemberEntity memberEntity = optionalMemberEntity.get();
        Long loginId = 1L;
        passportService.save(passportDTO, loginId);
        PassportDTO byLoginId = passportService.findByLoginId(loginId);
        assertThat(passportDTO.equals(byLoginId));
    }
}
