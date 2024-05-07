package com.MathEase.MathEase.Controller;

import com.MathEase.MathEase.Model.Options;
import com.MathEase.MathEase.Model.Questions;
import com.MathEase.MathEase.Model.QuizDTO;
import com.MathEase.MathEase.Model.Topic;
import com.MathEase.MathEase.Service.OptionService;
import com.MathEase.MathEase.Service.QuestionAttachmentService;
import com.MathEase.MathEase.Service.QuestionService;
import com.MathEase.MathEase.Service.TopicService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class QuizController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TopicService topicService;
    @Autowired
    private OptionService optionService;
    @Autowired
    private QuestionAttachmentService questionAttachmentService;

    @GetMapping("/student/quiz/{topicId}")
    public String getQuiz(HttpSession session,
                          Model model,
                          @PathVariable("topicId") Long topicId) {

        if (session.getAttribute("userId") == null || !session.getAttribute("role").equals("student")) {
            return "redirect:/login";
        }

        Topic topic = topicService.getTopicById(topicId);

        List<Questions> questions = questionService.getQuestionsByTopicId(topic);
        List<QuizDTO> quizDTO = new ArrayList<>();

        for (Questions question : questions) {
            QuizDTO quiz = new QuizDTO();
            quiz.setQuizId(question.getQuestionId());
            quiz.setQuiz(question.getQuestion());

            List<Options> options = optionService.getOptionsByQuestion(question);
            Collections.shuffle(options);

            quiz.setOptions(options);

            if (questionAttachmentService.isAttachmentPresent(question)) {
                quiz.setFilePath(questionAttachmentService.getAttachmentPath(question));
            }
            quizDTO.add(quiz);
        }
        Collections.shuffle(quizDTO);

        model.addAttribute("quizDTO", quizDTO);

        return "student/student-quiz";
    }

    @GetMapping("/student/quiz/result")
    public String getQuizResult(HttpSession session, Model model) {

        if (session.getAttribute("userId") == null || !session.getAttribute("role").equals("student")) {
            return "redirect:/login";
        }

        return "student/student-quiz-results";
    }

}
