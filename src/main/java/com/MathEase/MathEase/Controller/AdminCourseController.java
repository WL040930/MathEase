package com.MathEase.MathEase.Controller;

import com.MathEase.MathEase.Service.TopicService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.MathEase.MathEase.Model.Topic;
import java.util.List;

@Controller
public class AdminCourseController {

    @Autowired
    private MenuController menuController;

    @Autowired
    private TopicService topicService;

    @GetMapping("/admin/courses")
    public String showAdminCourses(HttpSession session, Model model) {

        // Check if user is logged in and is an admin
        if (session.getAttribute("userId") == null || !session.getAttribute("role").equals("admin")) {
            return "redirect:/admin/login";
        }

        // Set menu bar
        menuController.setMenuBar(session, model);

        // Get all topics
        List<Topic> topics = topicService.getAllTopics();
        model.addAttribute("topics", topics);
        return "admin/admin-courses";
    }

}
