package com.its.happy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
public class OrderTest {
    @Test
    @DisplayName("아임포트 테스트")
    @Transactional
    @Rollback(value = false)
    public void iamportTest() {

    }
}
