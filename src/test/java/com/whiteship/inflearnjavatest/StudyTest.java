package com.whiteship.inflearnjavatest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 클래스마다 인스턴스 공유
class StudyTest {
    int value = 1;

    @FastTest
    @DisplayName("스터디 만들기")
    void create() {
        System.out.println(this);
        System.out.println(value++);
        String testEnv = System.getenv("TEST_ENV");
        System.out.println(testEnv);
        Study study = new Study(10);
        assertEquals(10, study.getLimit());

    }
    @SlowTest
    void create1() {
        System.out.println(this);
        System.out.println(value++);
        System.out.println("create1");
    }
    @DisplayName("반복 테스트")
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
    void repeatTest(RepetitionInfo repetitionInfo){
        System.out.println(this);
        System.out.println(value++);
        System.out.println("test " + repetitionInfo.getCurrentRepetition() + " / " +
                repetitionInfo.getTotalRepetitions());
    }

    @DisplayName("반복 테스트 (동적 데이터)")
    @ParameterizedTest(name = "{index} {displayName} message 입력 :{0}")
    @ValueSource(strings = {"테스트를", "동적으로", "추가하여", "테스트."})
    @NullAndEmptySource // null과 empty 추가로 테스트해준다.
    void parameterizedTest(String message){
        // ValueSource 개수 만큼 테스트가 동작한다.
        System.out.println(message);
    }



    @BeforeAll
    void beforeAll() {
        System.out.println("beforeAll");
    }

    @AfterAll
    void afterAll() {
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