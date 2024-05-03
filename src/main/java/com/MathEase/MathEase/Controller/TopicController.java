package com.MathEase.MathEase.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TopicController {

    @Autowired
    private MenuController menuController;

    @GetMapping("topic/{topicId}")
    public String topic(@PathVariable Long topicId,
                        Model model,
                        HttpSession session) {

        if (session.getAttribute("userId") == null) {
            return "redirect:/login";
        }

        if (session.getAttribute("role").equals("student")) {
            // TODO
            setTopicAttributes(topicId, model);
            return "student/student-topic";
        } else {
            menuController.setMenuBar(session, model);
            setTopicAttributes(topicId, model);
            return "admin/admin-topic";
        }
    }

    private void setTopicAttributes(Long topicId, Model model) {
        String chapterTemplateName = "courses/chapter" + topicId;
        model.addAttribute("chapterTemplateName", chapterTemplateName);
    }

}
