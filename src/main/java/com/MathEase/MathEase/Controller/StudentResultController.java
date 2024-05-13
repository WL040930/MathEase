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

        if (session.getAttribute("userId") == null || !session.getAttribute("role").equals("student")) {
            return "redirect:/login";
        }

        User user = userService.getUserById((Long) session.getAttribute("userId"));

        List<ResultDTO> resultDTOs = new ArrayList<>();

        List<Topic> topics = topicRepository.getAllTopics();

        for (Topic topic : topics) {
            ResultDTO resultDTO = new ResultDTO();
            resultDTO.setChapter("Chapter " + topic.getTopicId());
            resultDTO.setChapterName(topic.getTopicName());
            resultDTO.setScore(userAnswerService.calculateScoreByUser(user, topic));
            resultDTOs.add(resultDTO);
        }

        System.out.println(resultDTOs);

        model.addAttribute("resultDTOs", resultDTOs);
        menuController.setMenuBar(session,model);
        return "student/student-results";
    }
}
