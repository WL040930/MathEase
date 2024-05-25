package com.MathEase.MathEase.Controller;

import com.MathEase.MathEase.Model.ResultDTO;
import com.MathEase.MathEase.Model.Topic;
import com.MathEase.MathEase.Model.User;
import com.MathEase.MathEase.Repository.TopicRepository;
import com.MathEase.MathEase.Service.UserAnswerService;
import com.MathEase.MathEase.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentResultController {

    @Autowired
    private MenuController menuController;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private UserAnswerService userAnswerService;
    @Autowired
    private UserService userService;

    @GetMapping("/student/results")
    public String getQuizResult(HttpSession session, Model model) {

        // Check if user is logged in and is a student
        if (session.getAttribute("userId") == null || !session.getAttribute("role").equals("student")) {
            return "redirect:/login";
        }

        // Get user
        User user = userService.getUserById((Long) session.getAttribute("userId"));

        // Create a list of ResultDTO
        List<ResultDTO> resultDTOs = new ArrayList<>();

        // Get all topics
        List<Topic> topics = topicRepository.getAllTopics();

        // Calculate score for each topic
        for (Topic topic : topics) {
            ResultDTO resultDTO = new ResultDTO();
            resultDTO.setChapter("Chapter " + topic.getTopicId());
            resultDTO.setChapterName(topic.getTopicName());
            resultDTO.setScore(userAnswerService.calculateScoreByUser(user, topic));
            resultDTOs.add(resultDTO);
        }

        // Add resultDTOs to model
        model.addAttribute("resultDTOs", resultDTOs);
        menuController.setMenuBar(session,model);
        return "student/student-results";
    }
}
