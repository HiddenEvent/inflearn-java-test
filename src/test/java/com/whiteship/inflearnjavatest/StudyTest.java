package com.whiteship.inflearnjavatest;

import org.junit.jupiter.api.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    @Test
    @DisplayName("스터디 만들기")
    void create() {
        String testEnv = System.getenv("TEST_ENV");
        System.out.println(testEnv);
        // LOCAL profile 일 경우만  하위 모든 테스트 코드 실행
        assumeTrue("LOCAL".equalsIgnoreCase(testEnv));

        // LOCAL profile 일 경우  아래 테스트 코드 실행
        assumingThat("LOCAL".equalsIgnoreCase(testEnv), () -> {
            // 테스트 코드
        });
        // LOCAL profile 일 경우  아래 테스트 코드 실행
        assumingThat("STG".equalsIgnoreCase(testEnv), () -> {
            // 테스트 코드
        });

    }

    @Test
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