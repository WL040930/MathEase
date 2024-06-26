package com.MathEase.MathEase.Controller;

import com.MathEase.MathEase.Model.Link;
import com.MathEase.MathEase.Model.Topic;
import com.MathEase.MathEase.Service.LinkService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.MathEase.MathEase.Repository.TopicRepository;

import java.util.List;

@Controller
public class TopicController {

    @Autowired
    private MenuController menuController;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private LinkService linkService;

    @GetMapping("topic/{topicId}")
    public String topic(@PathVariable Long topicId,
                        Model model,
                        HttpSession session) {

        // Check if user is logged in
        if (session.getAttribute("userId") == null) {
            return "redirect:/login";
        }

        // Check if user is a student
        if (session.getAttribute("role").equals("student")) {
            menuController.setMenuBar(session, model);
            setTopicAttributes(topicId, model);

            // Get topic by id
            Topic topic = topicRepository.findById(topicId).get();

            // Get links by topic
            List<Link> links = linkService.getLinksByTopic(topic);

            model.addAttribute("topic", topicId);
            model.addAttribute("links", links);

            return "student/student-topic";
        } else {
            menuController.setMenuBar(session, model);
            setTopicAttributes(topicId, model);
            return "admin/admin-topic";
        }
    }

    // Set topic attributes
    private void setTopicAttributes(Long topicId, Model model) {
        String chapterTemplateName = "courses/chapter" + topicId;
        model.addAttribute("chapterTemplateName", chapterTemplateName);
    }

}
