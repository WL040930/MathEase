package com.MathEase.MathEase.Service;

import com.MathEase.MathEase.Model.Options;
import com.MathEase.MathEase.Model.Topic;
import com.MathEase.MathEase.Model.User;
import com.MathEase.MathEase.Model.UserAnswer;
import com.MathEase.MathEase.Repository.UserAnswerRepository;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
public class UserAnswerService {

    private final UserAnswerRepository userAnswerRepository;

    public UserAnswerService(UserAnswerRepository userAnswerRepository) {
        this.userAnswerRepository = userAnswerRepository;
    }

    public Options getOptionsByUserAnswer(UserAnswer userAnswer) {
        return userAnswer.getOption();
    }

    // Get total number of user answers by question topic id
    public int getTotalNumbersOfUserAnswerByQuestion_TopicId(Topic topic) {
        return userAnswerRepository.countByQuestion_TopicId(topic);
    }

    // Get total number of user answers by question topic id and option is correct
    public int getTotalNumbersOfUserAnswerByQuestion_TopicId(Topic topic, User user) {
        return userAnswerRepository.countByUserAndQuestion_TopicId(user,topic);
    }

    // Get total number of user answers by question topic id and option is correct
    public int getTotalNumbersOfUserAnswerByQuestion_TopicIdAndOption_isCorrect(Topic topic, boolean isCorrect) {
        return userAnswerRepository.countByQuestion_TopicIdAndOption_isCorrect(topic,isCorrect);
    }

    // get total number of user answers by question topic id and option is correct  and user
    public int getTotalNumbersOfUserAnswerByQuestion_TopicIdAndOption_isCorrect(User user, Topic topic, boolean isCorrect) {
        return userAnswerRepository.countByUserAndQuestion_TopicIdAndOption_isCorrect(user,topic,isCorrect);
    }

    // count distinct users by topic
    public int countDistinctUsersByTopic(Topic topic) {
        return userAnswerRepository.countDistinctUsersByTopic(topic);
    }

    // calculate score by user
    public double calculateScoreByUser(User user, Topic topic) {
        int totalQuestions = getTotalNumbersOfUserAnswerByQuestion_TopicId(topic, user);
        int totalCorrectAnswers = getTotalNumbersOfUserAnswerByQuestion_TopicIdAndOption_isCorrect(user, topic, true);
        double score = (double) totalCorrectAnswers / totalQuestions * 100;
        DecimalFormat df = new DecimalFormat("#0.00");
        return Double.parseDouble(df.format(score));
    }
}
