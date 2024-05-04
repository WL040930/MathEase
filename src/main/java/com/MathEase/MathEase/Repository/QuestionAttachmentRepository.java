package com.MathEase.MathEase.Repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.MathEase.MathEase.Model.QuestionAttachment;

@Repository
public interface QuestionAttachmentRepository extends JpaRepository<QuestionAttachment, Long>{
}
