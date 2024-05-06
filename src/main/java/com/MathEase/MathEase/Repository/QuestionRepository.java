package com.MathEase.MathEase.Repository;

import com.MathEase.MathEase.Model.Questions;
import com.MathEase.MathEase.Model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Questions, Long>{

    Questions findByQuestionId(Long questionId);

    List<Questions> findByTopicId(Topic topicId);

    int countByTopicId(Topic topic);

}
