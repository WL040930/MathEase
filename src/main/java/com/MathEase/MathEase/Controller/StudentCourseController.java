package com.MathEase.MathEase.Controller;

import com.MathEase.MathEase.Model.Topic;
import com.MathEase.MathEase.Model.User;
import com.MathEase.MathEase.Service.AnswerService;
import com.MathEase.MathEase.Service.TopicService;
import com.MathEase.MathEase.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class StudentCourseController {

    @Autowired
    private final MenuController menuController;

    @Autowired
    private TopicService topicService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private UserService userService;

    public StudentCourseController(MenuController menuController) {
        this.menuController = menuController;
    }

    @GetMapping("/student/courses")
    public String studentCourses(HttpSession session, Model model) {

        // If user is not logged in or is not a student, redirect to login page
        if (session.getAttribute("userId") == null || !session.getAttribute("role").equals("student")) {
            return "redirect:/login";
        }

        // Set the menu bar
        menuController.setMenuBar(session, model);
        // Get all topics
        List<Topic> topics = topicService.getAllTopics();
        model.addAttribute("topics", topics);

        return "student/student-course";
    }

    @PostMapping("/api/topicAnswered")
    public ResponseEntity<Boolean> isFullyAnswered (@RequestParam("topicId") String topicId,
                                                    HttpSession session) {

        // If user is not logged in or is not a student, redirect to login page
        Topic topic = topicService.getTopicById(Long.parseLong(topicId));

        // Get the user
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.getUserById(userId);

        boolean isCompleted = answerService.isTopicFullyAnswered(topic, user);

        return ResponseEntity.ok(isCompleted);
    }
}

