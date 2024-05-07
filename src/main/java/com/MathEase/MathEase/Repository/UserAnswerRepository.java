package com.MathEase.MathEase.Repository;

import com.MathEase.MathEase.Model.Questions;
import com.MathEase.MathEase.Model.Topic;
import com.MathEase.MathEase.Model.User;
import com.MathEase.MathEase.Model.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long>{

    int countByQuestion_TopicIdAndUser(Topic topic, User user);

    boolean existsByUserAndQuestion(User user, Questions question);

    UserAnswer findByUserAndQuestion(User user, Questions question);
}