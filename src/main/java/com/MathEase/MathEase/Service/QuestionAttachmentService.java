package com.MathEase.MathEase.Service;

import com.MathEase.MathEase.Model.Questions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.MathEase.MathEase.Repository.QuestionAttachmentRepository;

@Service
public class QuestionAttachmentService {

    @Autowired
    private QuestionAttachmentRepository questionAttachmentRepository;

    public String getAttachmentPath(Questions questions) {
        if (questionAttachmentRepository.existsByQuestion(questions)) {
            return questionAttachmentRepository.findByQuestion(questions).getAttachmentFilename();
        } else {
            return null;
        }
    }

    public boolean isAttachmentPresent(Questions questions) {
        return questionAttachmentRepository.existsByQuestion(questions);
    }

}
