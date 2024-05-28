package com.MathEase.MathEase.Controller;

import com.MathEase.MathEase.Model.ResultDTO;
import com.MathEase.MathEase.Model.Topic;
import com.MathEase.MathEase.Repository.QuestionRepository;
import com.MathEase.MathEase.Repository.TopicRepository;
import com.MathEase.MathEase.Repository.UserRepository;
import com.MathEase.MathEase.Service.TopicService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.MathEase.MathEase.Service.UserAnswerService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminDashboardController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuController MenuController;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private TopicService topicService;
    @Autowired
    private UserAnswerService userAnswerService;

    @GetMapping("/admin/dashboard")
    public String showAdminDashboard(HttpSession session,
                                     RedirectAttributes redirectAttributes,
                                     Model model) {

        // Check if user is logged in and is an admin
        if (!SessionController.validateAdmin(session)) {
            redirectAttributes.addAttribute("errorMessage", "unauthorized");
            return "redirect:/login";
        }

        List<Topic> topics = topicService.getAllTopics();

        List<ResultDTO> resultDTOS = new ArrayList<>();

        for (Topic topic : topics) {
            ResultDTO resultDTO = new ResultDTO();
            resultDTO.setChapter(topic.getTopicId().toString());
            resultDTO.setChapterName(topic.getTopicName());
            double score = userAnswerService.calculateAverageScoreByTopic(topic);
            if (Double.isNaN(score)) {
                score = 0;
            }
            resultDTO.setScore(score);
            resultDTOS.add(resultDTO);
        }

        model.addAttribute("resultDTOs", resultDTOS);
        model.addAttribute("totalUser", userRepository.count());
        model.addAttribute("totalQuestion", questionRepository.count());
        model.addAttribute("totalTopic", topicRepository.count());
        // Set menu bar
        MenuController.setMenuBar(session, model);

        return "admin/admin-dashboard";
    }
}
