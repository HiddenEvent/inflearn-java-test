package com.whiteship.inflearnjavatest.study;



import com.whiteship.inflearnjavatest.domain.Member;
import com.whiteship.inflearnjavatest.domain.Study;
import com.whiteship.inflearnjavatest.member.MemberService;

import java.util.Optional;

public class StudyService {

    private final MemberService memberService;

    private final StudyRepository repository;

    public StudyService(MemberService memberService, StudyRepository repository) {
        assert memberService != null;
        assert repository != null;
        this.memberService = memberService;
        this.repository = repository;
    }

    public Study createNewStudy(Long memberId, Study study) {
        Optional<Member> member = memberService.findById(memberId);
        if (member.isPresent()) {
//            study.setOwnerId(memberId);
        } else {
            throw new IllegalArgumentException("Member doesn't exist for id: '" + memberId + "'");
        }
        Study newstudy = repository.save(study);
        memberService.notify(newstudy);
        return newstudy;
    }

    public Study openStudy(Study study) {
        study.open();
        Study openedStudy = repository.save(study);
        memberService.notify(openedStudy);
        return openedStudy;
    }

    public void hi() {

    }
}
