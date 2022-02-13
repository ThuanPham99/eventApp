package com.assignment.event.service;

import com.assignment.event.entity.Question;
import com.assignment.event.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    public List<Question> getQuestions(Integer eventID){
        return  questionRepository.findAllByEventIDAndStatusContains(eventID,"Active");
    }
    public  Question getQuestionByID(Integer id){
        return questionRepository.findByID(id);
    }
    public boolean add(Question question){
        if(questionRepository.save(question)!=null){
            return true;
        }
        return false;
    }
    public List<Question> getQuestionsWithGuestID(Integer guest){
        return questionRepository.findAllByGuestID(guest);
    }
    public List<Question> getAll(){
        return questionRepository.findAll();
    }

}
