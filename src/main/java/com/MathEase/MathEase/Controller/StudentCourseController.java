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

        if (session.getAttribute("userId") == null || !session.getAttribute("role").equals("student")) {
            return "redirect:/login";
        }

        menuController.setMenuBar(session, model);
        List<Topic> topics = topicService.getAllTopics();
        model.addAttribute("topics", topics);

        return "student/student-course";
    }

    @PostMapping("/api/topicAnswered")
    public ResponseEntity<Boolean> isFullyAnswered (@RequestParam("topicId") String topicId,
                                                    HttpSession session) {


        Topic topic = topicService.getTopicById(Long.parseLong(topicId));

        Long userId = (Long) session.getAttribute("userId");
        User user = userService.getUserById(userId);

        boolean isCompleted = answerService.isTopicFullyAnswered(topic, user);
        System.out.println("isCompleted: " + isCompleted);

        return  ResponseEntity.ok(isCompleted);
    }
}

