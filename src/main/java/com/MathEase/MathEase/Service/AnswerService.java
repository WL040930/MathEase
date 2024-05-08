package com.MathEase.MathEase.Service;

import com.MathEase.MathEase.Model.Questions;
import com.MathEase.MathEase.Model.Topic;
import com.MathEase.MathEase.Model.User;
import com.MathEase.MathEase.Model.UserAnswer;
import com.MathEase.MathEase.Repository.QuestionRepository;
import com.MathEase.MathEase.Repository.TopicRepository;
import com.MathEase.MathEase.Repository.UserAnswerRepository;
import com.MathEase.MathEase.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserAnswerRepository userAnswerRepository;

    public boolean isTopicFullyAnswered(Topic topic, User user) {

        int totalQuestions = questionRepository.countByTopicId(topic);

        int totalAnsweredQuestions = userAnswerRepository.countByQuestion_TopicIdAndUser(topic, user);

        if (totalQuestions == totalAnsweredQuestions) {
            return true;
        } else {
            return false;
        }

    }

    public boolean isRecordExisted (User user, Questions question) {
        return userAnswerRepository.existsByUserAndQuestion(user, question);
    }

    public List<UserAnswer> getAnswersByUserAndTopic(User user, Topic topic) {
        return userAnswerRepository.findByUserAndQuestion_TopicId(user, topic);
    }
}
