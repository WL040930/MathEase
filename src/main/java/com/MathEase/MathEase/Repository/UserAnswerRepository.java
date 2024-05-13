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

    int countByQuestion_TopicIdAndUser(Topic topic, User user);

    boolean existsByUserAndQuestion(User user, Questions question);

    UserAnswer findByUserAndQuestion(User user, Questions question);

    List<UserAnswer> findByUserAndQuestion_TopicId(User user, Topic topic);

    int countByQuestion_TopicId(Topic topic);

    int countByUserAndQuestion_TopicId(User user, Topic topic);

    int countByQuestion_TopicIdAndOption_isCorrect(Topic topic, boolean isCorrect);

    int countByUserAndQuestion_TopicIdAndOption_isCorrect(User user, Topic topic, boolean isCorrect);

    @Query("SELECT COUNT(DISTINCT ua.user) FROM UserAnswer ua WHERE ua.question.topicId = :topic")
    int countDistinctUsersByTopic(@Param("topic") Topic topic);
}