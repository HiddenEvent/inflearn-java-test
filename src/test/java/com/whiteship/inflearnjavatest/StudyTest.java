package com.whiteship.inflearnjavatest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    @Test
    @DisplayName("스터디 만들기")
    @EnabledOnJre({JRE.JAVA_8,JRE.JAVA_9, JRE.JAVA_10, JRE.JAVA_17 })
    void create() {
        String testEnv = System.getenv("TEST_ENV");
        System.out.println(testEnv);
        // LOCAL profile 일 경우만  하위 모든 테스트 코드 실행
        assumeTrue("LOCAL".equalsIgnoreCase(testEnv));
    }

    @Test
    @EnabledOnJre({JRE.OTHER })
    void create1() {
        System.out.println("create1");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("beforeAll");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("afterAll");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("beforeEach");
    }

    @AfterEach
    void afterEach() {
        System.out.println("afterEach");
    }


}