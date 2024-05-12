package com.MathEase.MathEase.Repository;

import com.MathEase.MathEase.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long>{

    int countByQuestion_TopicIdAndUser(Topic topic, User user);

    boolean existsByUserAndQuestion(User user, Questions question);

    UserAnswer findByUserAndQuestion(User user, Questions question);

    List<UserAnswer> findByUserAndQuestion_TopicId(User user, Topic topic);

    int countByQuestion_TopicId(Topic topic);

    int countByQuestion_TopicIdAndOption_isCorrect(Topic topic, boolean isCorrect);

}