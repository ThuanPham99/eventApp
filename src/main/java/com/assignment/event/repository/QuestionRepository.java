package com.assignment.event.repository;

import com.assignment.event.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Integer> {
    List<Question> findAllByEventID(Integer id);
    Question findByID(Integer id);
    List<Question> findAllByEventIDAndStatusContains(Integer eventId,String status);
    List<Question> findAllByGuestID(Integer guestId);
    List<Question> findAll();
}
