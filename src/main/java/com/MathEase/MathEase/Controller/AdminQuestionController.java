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

    @GetMapping("/admin/questions/{topicId}")
    public ResponseEntity<Topic> getTopicById(@PathVariable Long topicId) {
        System.out.println("topicId: " + topicId);
        try {
            Topic topic = topicService.getTopicById(topicId);
            if (topic != null) {
                return new ResponseEntity<>(topic, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
