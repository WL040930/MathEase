package com.MathEase.MathEase.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuizController {

    @Autowired
    private MenuController menuController;

    @GetMapping("/student/quiz/{topicId}")
    public String getQuiz(HttpSession session,
                          Model model,
                          @PathVariable("topicId") int topicId) {

        if (session.getAttribute("userId") == null || !session.getAttribute("role").equals("student")) {
            return "redirect:/login";
        }

        menuController.setMenuBar(session, model);
        return "student/student-quiz";
    }

}
