package com.MathEase.MathEase.Repository;

import com.MathEase.MathEase.Model.Questions;
import com.MathEase.MathEase.Model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Questions, Long>{

    // Find question by questionId
    Questions findByQuestionId(Long questionId);

    // Find all questions by topic
    List<Questions> findByTopicId(Topic topicId);

    // Find all questions by topic and difficulty
    int countByTopicId(Topic topic);

}
