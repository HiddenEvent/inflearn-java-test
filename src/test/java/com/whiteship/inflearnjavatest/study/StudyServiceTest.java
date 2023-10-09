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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

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
        when(memberService.findById(any()))
                .thenReturn(Optional.of(member)) // 1번째 호출
                .thenThrow(new RuntimeException()) // 2번째 호출
                .thenReturn(Optional.empty()); // 3번째 호출
        Optional<Member> byId = memberService.findById(1L);
        // 1번째 호출
        assertEquals("a@a.com", byId.get().getEmail());
        // 2번째 호출
        assertThrows(RuntimeException.class, () -> {
            memberService.findById(1L);
        });
        // 3번째 호출
        assertEquals(Optional.empty(), memberService.findById(1L));

//        Study study = new Study(10, "java");
//        when(studyRepository.save(any())).thenReturn(study);


    }

    @Test
    void openStudy() {
    }

    @Test
    void hi() {
    }
}