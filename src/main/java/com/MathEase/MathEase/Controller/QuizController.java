package com.MathEase.MathEase.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuizController {

    @GetMapping("/student/quiz")
    public String getQuiz() {
        return "student/student-quiz";
    }

}
