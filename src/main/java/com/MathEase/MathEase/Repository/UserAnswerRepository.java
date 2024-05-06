package com.MathEase.MathEase.Repository;

import com.MathEase.MathEase.Model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.MathEase.MathEase.Model.UserAnswer;
import com.MathEase.MathEase.Model.User;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long>{

    Long countByQuestion_TopicIdAndUser(Topic topic, User user);

}