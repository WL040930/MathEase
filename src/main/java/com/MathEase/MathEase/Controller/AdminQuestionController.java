package com.MathEase.MathEase.Controller;

import com.MathEase.MathEase.Model.Options;
import com.MathEase.MathEase.Model.QuestionDTO;
import com.MathEase.MathEase.Model.Questions;
import com.MathEase.MathEase.Model.Topic;
import com.MathEase.MathEase.Repository.QuestionRepository;
import com.MathEase.MathEase.Service.OptionService;
import com.MathEase.MathEase.Service.QuestionAttachmentService;
import com.MathEase.MathEase.Service.QuestionService;
import com.MathEase.MathEase.Service.TopicService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminQuestionController {

    @Autowired
    private MenuController menuController;

    @Autowired
    private TopicService topicService;
    
    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionService optionService;

    @Autowired
    private QuestionAttachmentService questionAttachmentService;

    @GetMapping("/admin/questions")
    public String getAdminQuestions(HttpSession session, Model model) {

        // Check if user is logged in and is an admin
        if (session.getAttribute("userId") == null || !session.getAttribute("role").equals("admin")) {
            return "redirect:/login";
        }

        // Set menu bar
        List<Topic> topics = topicService.getAllTopics();
        model.addAttribute("topics", topics);
        menuController.setMenuBar(session, model);
        return "admin/admin-questions";
    }

    @GetMapping("/api/topics/{topicId}")
    public ResponseEntity<Topic> getTopicById(@PathVariable Long topicId) {
        try {
            // Get topic by id
            Topic topic = topicService.getTopicById(topicId);

            // Check if topic exists
            if (topic != null) {
                return ResponseEntity.ok(topic);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/api/questions/{topicId}")
    public ResponseEntity<List<Questions>> getQuestionsByTopicId(@PathVariable Long topicId) {
        try {
            // Get questions by topic id
            Topic topic = topicService.getTopicById(topicId);

            //  Check if topic exists
            List<Questions> questions = questionService.getQuestionsByTopicId(topic);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/api/question/{questionId}")
    public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable Long questionId) {
        try {
            // Get question by id
            Questions questions = questionRepository.findById(questionId).orElse(null);

            // Check if question exists
            if (questions != null) {
                QuestionDTO questionDTO = new QuestionDTO();

                // Set question details
                questionDTO.setQuestionId(questions.getQuestionId());
                questionDTO.setQuestion(questions.getQuestion());
                questionDTO.setCorrectAnswer(optionService.getCorrectOption(questions).getOption());

                // Set wrong answers
                ArrayList<Options> wrongOptions = optionService.getWrongOptions(questions);
                questionDTO.setWrongAnswer1(wrongOptions.get(0).getOption());
                questionDTO.setWrongAnswer2(wrongOptions.get(1).getOption());
                questionDTO.setWrongAnswer3(wrongOptions.get(2).getOption());

                // Set picture path
                if (questionAttachmentService.getAttachmentPath(questions) != null) {
                    questionDTO.setPicturePath(questionAttachmentService.getAttachmentPath(questions));
                }

                //  Return question details
                return ResponseEntity.ok(questionDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/api/deleteQuestion")
    public ResponseEntity<String> deleteQuestion(Long questionId) {
        try {
            // Delete question by id
            questionService.deleteQuestionById(questionId);
            return ResponseEntity.ok("Question deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
