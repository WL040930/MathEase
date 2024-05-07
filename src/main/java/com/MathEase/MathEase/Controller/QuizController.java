package com.MathEase.MathEase.Controller;

import com.MathEase.MathEase.Model.*;
import com.MathEase.MathEase.Repository.UserAnswerRepository;
import com.MathEase.MathEase.Service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @Autowired
    private AnswerService answerService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserAnswerRepository userAnswerRepository;

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

    @PostMapping("/api/checkAnswer")
    public ResponseEntity<Boolean> checkAnswer (HttpSession session,
                                                @RequestParam("questionId") Long questionId,
                                                @RequestParam("optionId") Long optionId) {

        // check if the record existed
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.getUserById(userId);
        Questions question = questionService.getQuestionById(questionId);
        Options option = optionService.getOptionById(optionId);

        if (answerService.isRecordExisted(user, question)) {
            UserAnswer userAnswer = userAnswerRepository.findByUserAndQuestion(user, question);
            userAnswer.setOption(option);
            userAnswerRepository.save(userAnswer);
        } else {
            UserAnswer userAnswer = new UserAnswer();
            userAnswer.setUser(user);
            userAnswer.setQuestion(question);
            userAnswer.setOption(option);
            userAnswerRepository.save(userAnswer);
        }


        if (optionService.isAnswerCorrect(optionId)) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }


    }

}
