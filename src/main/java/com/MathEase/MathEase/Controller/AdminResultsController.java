package com.MathEase.MathEase.Controller;

import com.MathEase.MathEase.Model.AdminResultsDTO;
import com.MathEase.MathEase.Model.Topic;
import com.MathEase.MathEase.Service.TopicService;
import com.MathEase.MathEase.Service.UserAnswerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AdminResultsController {

    @Autowired
    private MenuController menuController;

    @Autowired
    private TopicService topicService;
    @Autowired
    private UserAnswerService userAnswerService;

    @GetMapping("/admin/results")
    public String getAdminResults(HttpSession session, Model model) {

        // Check if user is logged in and is an admin
        if (session.getAttribute("userId") == null || !session.getAttribute("role").equals("admin")) {
            return "redirect:/login";
        }

        // Get all topics
        List<Topic> topics = topicService.getAllTopics();

        // Add topics to model
        model.addAttribute("topics", topics);
        menuController.setMenuBar(session, model);
        return "admin/admin-result";
    }

    @GetMapping("/api/results/{topicId}")
    public ResponseEntity<AdminResultsDTO> getResults(@PathVariable("topicId") Long topicId) {

        // Check if topicId is null
        if (topicId == null) {
            return ResponseEntity.badRequest().build();
        }

        // Get results
        AdminResultsDTO adminResultsDTO = new AdminResultsDTO();
        Topic topic = topicService.getTopicById(topicId);

        adminResultsDTO.setTotalAnswers(userAnswerService.getTotalNumbersOfUserAnswerByQuestion_TopicId(topic));
        if (adminResultsDTO.getTotalAnswers() == 0) {
            // If no answers are found, return 0
            adminResultsDTO.setAverageScore(0);
            adminResultsDTO.setTotalUsers(0);
            adminResultsDTO.setCorrectAnswers(0);
            adminResultsDTO.setIncorrectAnswers(0);
            return ResponseEntity.ok(adminResultsDTO);
        }

        // Get correct and incorrect answers
        adminResultsDTO.setCorrectAnswers(userAnswerService.getTotalNumbersOfUserAnswerByQuestion_TopicIdAndOption_isCorrect(topic, true));
        adminResultsDTO.setIncorrectAnswers(adminResultsDTO.getTotalAnswers() - adminResultsDTO.getCorrectAnswers());
        double percentage = (double) adminResultsDTO.getCorrectAnswers() / adminResultsDTO.getTotalAnswers() * 100;
        adminResultsDTO.setAverageScore(percentage);
        adminResultsDTO.setTotalUsers(userAnswerService.countDistinctUsersByTopic(topic));

        System.out.println(adminResultsDTO);
        return ResponseEntity.ok(adminResultsDTO);
    }
}
