package com.MathEase.MathEase.Controller;

import com.MathEase.MathEase.Model.Topic;
import com.MathEase.MathEase.Model.User;
import com.MathEase.MathEase.Model.UserAnswer;
import com.MathEase.MathEase.Service.AnswerService;
import com.MathEase.MathEase.Service.QuestionService;
import com.MathEase.MathEase.Service.TopicService;
import com.MathEase.MathEase.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class StudentQuizResultsController {

    @Autowired
    private MenuController menuController;

    @Autowired
    private TopicService topicService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;
    @Autowired
    private AnswerService answerService;

    @GetMapping("/student/congratulations")
    public String Congrats(HttpSession session, Model model) {

        if (session.getAttribute("userId") == null || !session.getAttribute("role").equals("student")) {
            return "redirect:/login";
        }

        return "student/completion-page";
    }

    @GetMapping("/student/quiz")
    public String getQuiz(HttpSession session, Model model) {

        if (session.getAttribute("userId") == null || !session.getAttribute("role").equals("student")) {
            return "redirect:/login";
        }

        List<Topic> topics = topicService.getAllTopics();
        model.addAttribute("topics", topics);
        menuController.setMenuBar(session, model);
        return "student/student-quiz-results";
    }

    @GetMapping("/api/questionsFetch/{topicId}")
    public ResponseEntity<UserAnswer> getQuizResults (HttpSession session,
                                                      Model model,
                                                      @PathVariable String topicId) {

        if (session.getAttribute("userId") == null || !session.getAttribute("role").equals("student")) {
            return ResponseEntity.badRequest().build();
        }

        Topic topic = topicService.getTopicById(Long.parseLong(topicId));
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.getUserById(userId);

        if (answerService.isTopicFullyAnswered(topic, user)) {
            return ResponseEntity.ok(null);
        }

        return ResponseEntity.ok(new UserAnswer());
    }


}
