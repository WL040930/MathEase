package com.MathEase.MathEase.Service;

import com.MathEase.MathEase.Model.Questions;
import com.MathEase.MathEase.Model.Topic;
import com.MathEase.MathEase.Repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public List<Questions> getQuestionsByTopicId(Topic topic) {
        return questionRepository.findByTopicId(topic);
    }



}
