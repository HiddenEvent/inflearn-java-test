package com.whiteship.inflearnjavatest.study;

import com.whiteship.inflearnjavatest.domain.Member;
import com.whiteship.inflearnjavatest.domain.Study;
import com.whiteship.inflearnjavatest.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {
//    @Mock MemberService memberService;
//    @Mock StudyRepository studyRepository;

    @Test
    void createNewStudy(@Mock MemberService memberService,
                        @Mock StudyRepository studyRepository) {

        Member member = new Member();
        member.setId(1L);
        member.setEmail("a@a.com");
        // 아래 코드는 MemberService의 findById 메소드가 호출될 때 member를 리턴해라는 stubbing
        when(memberService.findById(1L)).thenReturn(Optional.of(member));

        // 아래 코드는 StudyRepository의 save 메소드가 호출될 때 study를 리턴해라는 stubbing
        Study study = new Study(10, "java");
        when(studyRepository.save(any())).thenReturn(study);

        // StudyService createNewStudy 테스트 코드
        StudyService studyService = new StudyService(memberService, studyRepository);
        studyService.createNewStudy(1L, study);
        assertEquals(member.getId(), study.getOwnerId());

        // memberService의 notify 메소드가 1번 호출되었는지 검증
        verify(memberService, times(1)).notify(study);
        // memberService의 validate 메소드가 한번도 호출되지 않았는지 검증
        verify(memberService, never()).validate(any());


    }

    @Test
    void openStudy() {
    }

    @Test
    void hi() {
    }
}