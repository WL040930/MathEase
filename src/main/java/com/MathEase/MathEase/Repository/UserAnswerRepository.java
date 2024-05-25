package com.MathEase.MathEase.Repository;

import com.MathEase.MathEase.Model.Questions;
import com.MathEase.MathEase.Model.Topic;
import com.MathEase.MathEase.Model.User;
import com.MathEase.MathEase.Model.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long>{

    // Find all user answers by user
    int countByQuestion_TopicIdAndUser(Topic topic, User user);

    // check if user has answered a question
    boolean existsByUserAndQuestion(User user, Questions question);

    // Find user answer by user and question
    UserAnswer findByUserAndQuestion(User user, Questions question);

    // Find all user answers by user
    List<UserAnswer> findByUserAndQuestion_TopicId(User user, Topic topic);

    // Find all user answers by user
    int countByQuestion_TopicId(Topic topic);

    // count all user answers by user and topic
    int countByUserAndQuestion_TopicId(User user, Topic topic);

    // count all user answers by topic and isCorrect
    int countByQuestion_TopicIdAndOption_isCorrect(Topic topic, boolean isCorrect);

    // count all user answers by user and topic and isCorrect
    int countByUserAndQuestion_TopicIdAndOption_isCorrect(User user, Topic topic, boolean isCorrect);

    // query to count all distinct users by topic
    @Query("SELECT COUNT(DISTINCT ua.user) FROM UserAnswer ua WHERE ua.question.topicId = :topic")
    int countDistinctUsersByTopic(@Param("topic") Topic topic);
}