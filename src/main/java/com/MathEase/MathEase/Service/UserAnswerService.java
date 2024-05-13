package com.MathEase.MathEase.Service;

import com.MathEase.MathEase.Model.Topic;
import com.MathEase.MathEase.Model.UserAnswer;
import com.MathEase.MathEase.Repository.UserAnswerRepository;
import org.springframework.stereotype.Service;
import com.MathEase.MathEase.Model.Options;

@Service
public class UserAnswerService {

    private final UserAnswerRepository userAnswerRepository;

    public UserAnswerService(UserAnswerRepository userAnswerRepository) {
        this.userAnswerRepository = userAnswerRepository;
    }

    public Options getOptionsByUserAnswer(UserAnswer userAnswer) {
        return userAnswer.getOption();
    }

    public int getTotalNumbersOfUserAnswerByQuestion_TopicId(Topic topic) {
        return userAnswerRepository.countByQuestion_TopicId(topic);
    }

    public int getTotalNumbersOfUserAnswerByQuestion_TopicIdAndOption_isCorrect(Topic topic, boolean isCorrect) {
        return userAnswerRepository.countByQuestion_TopicIdAndOption_isCorrect(topic,isCorrect);
    }

    public int countDistinctUsersByTopic(Topic topic) {
        return userAnswerRepository.countDistinctUsersByTopic(topic);
    }
}
