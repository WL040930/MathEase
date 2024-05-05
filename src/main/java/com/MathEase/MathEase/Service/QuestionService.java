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

    public Questions getQuestionById(Long questionId) {
        return questionRepository.findById(questionId).orElse(null);
    }

    public Questions deleteQuestionById(Long questionId) {
        Questions question = questionRepository.findById(questionId).orElse(null);
        if (question != null) {
            questionRepository.delete(question);
        }
        return question;
    }

}
