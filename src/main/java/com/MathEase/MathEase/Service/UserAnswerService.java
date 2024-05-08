package com.MathEase.MathEase.Service;

import com.MathEase.MathEase.Model.UserAnswer;
import org.springframework.stereotype.Service;
import com.MathEase.MathEase.Model.Options;

@Service
public class UserAnswerService {

    public Options getOptionsByUserAnswer(UserAnswer userAnswer) {
        return userAnswer.getOption();
    }

}
