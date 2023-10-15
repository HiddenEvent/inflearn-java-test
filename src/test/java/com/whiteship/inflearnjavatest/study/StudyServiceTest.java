package com.whiteship.inflearnjavatest.study;

import com.whiteship.inflearnjavatest.domain.Member;
import com.whiteship.inflearnjavatest.domain.Study;
import com.whiteship.inflearnjavatest.domain.StudyStatus;
import com.whiteship.inflearnjavatest.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
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
@ContextConfiguration(initializers = StudyServiceTest.ContainerPropertyInitializer.class)
class StudyServiceTest {
    @Mock
    MemberService memberService;
    @Autowired
    StudyRepository studyRepository;
    @Value("${container.port}")
    int port;

    // DB Contianer 생성 방법
    @Container
    static DockerComposeContainer composeContainer = new DockerComposeContainer(
            new File("src/test/resources/docker-compose.yml"))
            .withExposedService("study-db", 5432)
            ;

    @Test
    void createNewStudy() {
        log.info("============== docker-compose port : {}", port);

        // given
        StudyService studyService = new StudyService(memberService, studyRepository);
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
        log.info("============== docker-compose port : {}", port);
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

    // TestContainers, 컨테이너 정보를 스프링 테스트에서 사용할 수 있게 해주는 설정
    static class ContainerPropertyInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            System.out.println("============== initialize");
            TestPropertyValues.of(
                    "container.port=" + composeContainer.getServicePort("study-db", 5432) // docker-compose 설정한 컨테이너의 포트를 가져온다.
            ).applyTo(applicationContext.getEnvironment());

        }
    }
}