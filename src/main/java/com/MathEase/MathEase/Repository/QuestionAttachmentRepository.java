package com.MathEase.MathEase.Repository;

import com.MathEase.MathEase.Model.Questions;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.MathEase.MathEase.Model.QuestionAttachment;

@Repository
public interface QuestionAttachmentRepository extends JpaRepository<QuestionAttachment, Long>{

    // Find question attachment by question
    QuestionAttachment findByQuestion(Questions question);

    // Check if question attachment exists by question
    boolean existsByQuestion(Questions question);

}
