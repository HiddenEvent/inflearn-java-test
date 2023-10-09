package com.whiteship.inflearnjavatest;

import org.junit.jupiter.api.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    @Test
    @DisplayName("스터디 만들기")
    void create() {
        assertAll(
                // assertTimeout : 타임아웃 테스트 시 시간이 넘어가도 끝날때 까지 기다림
                () -> assertTimeout(Duration.ofMillis(100), () -> {
                    new Study(10);
                    Thread.sleep(300);
                }),
                
                // assertTimeoutPreemptively : 타임아웃 테스트 시 시간이 넘어가면 즉시 종료
                () -> assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
                    new Study(10);
                    Thread.sleep(300);
                })
        );
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