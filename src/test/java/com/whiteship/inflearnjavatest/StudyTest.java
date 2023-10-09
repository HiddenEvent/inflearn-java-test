package com.whiteship.inflearnjavatest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(FindSlowTestExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {
    int value = 1;

    @FastTest
    @DisplayName("Fast 스터디 만들기")
    void create() {
        System.out.println(this);
        System.out.println(value++);
        String testEnv = System.getenv("TEST_ENV");
        System.out.println(testEnv);
        Study study = new Study(10);
        assertEquals(10, study.getLimit());

    }
    @Test
    @DisplayName("Slow 스터디 만들기")
    void create1() throws InterruptedException {
        Thread.sleep(1005L);
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
        // 테스트용 코드 아무거나 만들어줘
        System.out.println(this);

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