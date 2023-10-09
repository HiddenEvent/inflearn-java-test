package com.whiteship.inflearnjavatest.study;


import com.whiteship.inflearnjavatest.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {

}
