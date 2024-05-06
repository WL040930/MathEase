package com.MathEase.MathEase.Controller;

import com.MathEase.MathEase.Model.Topic;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import com.MathEase.MathEase.Service.TopicService;

import java.util.List;

@Controller
public class StudentCourseController {

    @Autowired
    private final MenuController menuController;

    @Autowired
    private TopicService topicService;

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

}
