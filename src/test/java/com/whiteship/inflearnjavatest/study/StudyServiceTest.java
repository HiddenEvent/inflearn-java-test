package com.whiteship.inflearnjavatest.study;

import com.whiteship.inflearnjavatest.domain.Member;
import com.whiteship.inflearnjavatest.domain.Study;
import com.whiteship.inflearnjavatest.domain.StudyStatus;
import com.whiteship.inflearnjavatest.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@Testcontainers
@Slf4j
class StudyServiceTest {
    @Mock MemberService memberService;
    @Autowired
    StudyRepository studyRepository;
    // DB Contianer 생성 방법
    @Container
    static GenericContainer postgreSQLContainer = new GenericContainer("postgres:13.3")
            .withExposedPorts(5432)
            .withEnv("POSTGRES_DB", "studytest")
            .withEnv("POSTGRES_HOST_AUTH_METHOD", "trust");
//            .waitingFor(Wait.forLogMessage(".*database system is ready to accept connections.*", 2));
    @BeforeAll
    static void beforeAll() {
        Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(log);
        postgreSQLContainer.followOutput(logConsumer);
//        postgreSQLContainer.start();
//        System.out.println(postgreSQLContainer.getJdbcUrl());
//        System.out.println(postgreSQLContainer.getUsername());
//        System.out.println(postgreSQLContainer.getPassword());
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("============= beforeEach");
        System.out.println(postgreSQLContainer.getMappedPort(5432)); // 5432에 매핑된 포트를 가져온다.
        System.out.println(postgreSQLContainer.getLogs());
        studyRepository.deleteAll();
    }
    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @Test
    void createNewStudy() {
        StudyService studyService = new StudyService(memberService, studyRepository);

        // given
        Member member = new Member(1L, "a@a.com");
        // 아래 코드는 MemberService의 findById 메소드가 호출될 때 member를 리턴해라는 stubbing
        given(memberService.findById(1L)).willReturn(Optional.of(member));

        // 아래 코드는 StudyRepository의 save 메소드가 호출될 때 study를 리턴해라는 stubbing
        Study study = new Study(10, "java");
//        given(studyRepository.save(any())).willReturn(study);




        // when
        // StudyService createNewStudy
        studyService.createNewStudy(1L, study);

        // then
        assertEquals(member.getId(), study.getOwnerId());
        // memberService의 notify 메소드가 1번 호출되었는지 검증
        then(memberService).should(times(1)).notify(study);
        // memberService의 validate 메소드가 한번도 호출되지 않았는지 검증
        then(memberService).should(never()).validate(any());

    }

    @Test
    void openStudy() {
        //given
        StudyService studyService = new StudyService(memberService, studyRepository);
        Study study = new Study(10, "test");
        assertNull(study.getOpenedDateTime());

        //when
        studyService.openStudy(study);

        //then
        assertEquals(StudyStatus.OPENED, study.getStatus());
        assertEquals(study.getOpenedDateTime(), study.getOpenedDateTime());

        // memberService의 notify 메소드가 1번 호출되었는지 검증
        then(memberService).should(times(1)).notify(study);
    }
}