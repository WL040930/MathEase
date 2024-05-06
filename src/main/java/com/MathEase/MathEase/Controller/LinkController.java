package com.MathEase.MathEase.Controller;

import com.MathEase.MathEase.Model.Link;
import com.MathEase.MathEase.Model.Topic;
import com.MathEase.MathEase.Service.LinkService;
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
public class LinkController {

    @Autowired
    private final MenuController menuController;

    @Autowired
    private TopicService topicService;
    @Autowired
    private LinkService linkService;

    public LinkController(MenuController menuController) {
        this.menuController = menuController;
    }

    @GetMapping("/admin/link")
    public String adminLink(HttpSession session, Model model) {

        if (session.getAttribute("userId") == null || !session.getAttribute("role").equals("admin")) {
            return "redirect:/login";
        }

        List<Topic> topics = topicService.getAllTopics();
        model.addAttribute("topics", topics);
        menuController.setMenuBar(session, model);

        return "admin/admin-link";
    }


    @GetMapping("/api/links/{topicId}")
    public ResponseEntity<List<Link>> getQuestionsByTopicId(@PathVariable Long topicId) {
        try {
            Topic topic = topicService.getTopicById(topicId);
            List<Link> links = linkService.getLinksByTopic(topic);
            return ResponseEntity.ok(links);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
