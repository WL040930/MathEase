package com.MathEase.MathEase.Controller;

import com.MathEase.MathEase.Model.Topic;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.MathEase.MathEase.Service.TopicService;

import java.util.List;

@Controller
public class LinkController {

    @Autowired
    private final MenuController menuController;

    @Autowired
    private TopicService topicService;

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

}
