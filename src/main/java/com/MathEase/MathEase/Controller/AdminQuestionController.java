package com.MathEase.MathEase.Controller;

import com.MathEase.MathEase.Model.Topic;
import com.MathEase.MathEase.Service.TopicService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AdminQuestionController {

    @Autowired
    private MenuController menuController;

    @Autowired
    private TopicService topicService;

    @GetMapping("/admin/questions")
    public String getAdminQuestions(HttpSession session, Model model) {
        if (session.getAttribute("userId") == null || !session.getAttribute("role").equals("admin")) {
            return "redirect:/login";
        }
        List<Topic> topics = topicService.getAllTopics();
        model.addAttribute("topics", topics);
        menuController.setMenuBar(session, model);
        return "admin/admin-questions";
    }

    @GetMapping("/api/topics/{topicId}")
    public ResponseEntity<Topic> getTopicById(@PathVariable Long topicId) {
        try {
            Topic topic = topicService.getTopicById(topicId);
            if (topic != null) {
                return ResponseEntity.ok(topic);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
