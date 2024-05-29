package com.MathEase.MathEase.Controller;

import com.MathEase.MathEase.Model.ResultDTO;
import com.MathEase.MathEase.Model.Topic;
import com.MathEase.MathEase.Model.User;
import com.MathEase.MathEase.Repository.TopicRepository;
import com.MathEase.MathEase.Repository.UserAnswerRepository;
import com.MathEase.MathEase.Repository.UserRepository;
import com.MathEase.MathEase.Service.AnswerService;
import com.MathEase.MathEase.Service.TopicService;
import com.MathEase.MathEase.Service.UserAnswerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentDashboardController {

    private final MenuController menuController;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final TopicService topicService;
    private final AnswerService answerService;
    private final UserAnswerRepository userAnswerRepository;
    private final UserAnswerService userAnswerService;

    public StudentDashboardController(MenuController menuController, UserRepository userRepository, TopicRepository topicRepository, TopicService topicService, AnswerService answerService, UserAnswerRepository userAnswerRepository, UserAnswerService userAnswerService) {
        this.menuController = menuController;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.topicService = topicService;
        this.answerService = answerService;
        this.userAnswerRepository = userAnswerRepository;
        this.userAnswerService = userAnswerService;
    }

    @GetMapping("/student/dashboard")
    public String showStudentDashboard(HttpSession session, Model model) {

        // check session
        if (session.getAttribute("userId") == null || !session.getAttribute("role").equals("student")) {
            return "redirect:/login?error=Unauthorized";
        }

        // retrieve user inforation
        long userId = (long) session.getAttribute("userId");
        User user = userRepository.findByUserId(userId);
        String username = userRepository.findByUserId(userId).getUsername();

        // return completion status
        long totalTopic = topicRepository.count();
        List<Topic> topics = topicService.getAllTopics();

        int completion = 0;
        for (Topic topic : topics) {
            boolean isCompleted = answerService.isTopicFullyAnswered(topic, user);
            if (isCompleted) {
                completion++;
            }
        }

        // return average marks
        long totalQuestions = userAnswerRepository.countByUser(user);
        long totalCorrectAnswers = userAnswerRepository.countByUserAndOption_isCorrect(user, true);

        double averageMarks = ((double) totalCorrectAnswers / totalQuestions) * 100;
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedAverageMarks = df.format(averageMarks);

        // return each chapter status
        List<ResultDTO> resultDTOS = new ArrayList<>();
        List<Topic> allTopics = topicService.getAllTopics();

        for (Topic topic : allTopics) {
            ResultDTO resultDTO = new ResultDTO();
            resultDTO.setChapter(topic.getTopicId().toString());
            resultDTO.setChapterName(topic.getTopicName());
            double score = userAnswerService.calculateAverageScoreByTopic(topic, user);
            if (Double.isNaN(score)) {
                score = 0;
            }
            resultDTO.setScore(score);
            resultDTOS.add(resultDTO);
        }

        model.addAttribute("resultDTOs", resultDTOS);
        model.addAttribute("overallResult", formattedAverageMarks);
        model.addAttribute("completion", completion);
        model.addAttribute("totalTopic", totalTopic);
        model.addAttribute("User", username);
        model.addAttribute("joinedDate", user.getJoinedDate());
        menuController.setMenuBar(session, model);

        return "student/student-dashboard";
    }

}
