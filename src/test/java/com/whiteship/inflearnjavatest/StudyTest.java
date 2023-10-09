package com.whiteship.inflearnjavatest;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    @Test
    @DisplayName("스터디 만들기")
    void create() {
        Study study = new Study();

        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus()
                        , () -> "스터디를 처음 만들면 상태값이 DRAFT여야 한다."),
                () -> assertTrue(study.getLimit() > 0
                        , () -> "최대 참석인원은 0보다 커야한다.")
       );
        // () -> "스터디를 처음 만들면 상태값이 DRAFT여야 한다."
        // 위 코드처럼 표현하는 이유는 ?=> 람다로 사용하는 이유는 실패했을 경우에만 해당 문자열 연산이 일어나기 때문인다.

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