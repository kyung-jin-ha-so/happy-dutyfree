package com.its.happy;

import com.its.happy.dto.DepartureDTO;
import com.its.happy.entity.MemberEntity;
import com.its.happy.repository.MemberRepository;
import com.its.happy.service.DepartureService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DepartureTest {
    @Autowired
    private DepartureService departureService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("출국정보 저장 테스트")
    @Transactional
    @Rollback(value = false)
    public void departureSaveTest() {
        DepartureDTO departureDTO = new DepartureDTO("인천공항", "2022-07-19 22:00:00", "KE123");
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail("abc123@gmail.com");
        memberEntity.setMemberPassword("1234");
        memberEntity.setMemberName("홍길동");
        memberEntity.setMemberMobile("010-1234-1234");
        memberEntity.setMemberTier("브론즈");
        memberRepository.save(memberEntity);
        Long loginId = 1L;
        Long departureId = departureService.save(departureDTO, loginId);
        DepartureDTO departureDTO1 = departureService.findById(departureId);
        assertThat(departureDTO.equals(departureDTO1));
    }
}
