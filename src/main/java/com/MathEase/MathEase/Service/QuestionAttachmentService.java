package com.MathEase.MathEase.Service;

import com.MathEase.MathEase.Model.Questions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.MathEase.MathEase.Repository.QuestionAttachmentRepository;

@Service
public class QuestionAttachmentService {

    @Autowired
    private QuestionAttachmentRepository questionAttachmentRepository;

    // get the attachment path of the question
    public String getAttachmentPath(Questions questions) {
        if (questionAttachmentRepository.existsByQuestion(questions)) {
            return questionAttachmentRepository.findByQuestion(questions).getAttachmentFilename();
        } else {
            return null;
        }
    }

    // check if the attachment is present
    public boolean isAttachmentPresent(Questions questions) {
        return questionAttachmentRepository.existsByQuestion(questions);
    }

}
