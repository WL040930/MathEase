package com.MathEase.MathEase.Controller;

import com.MathEase.MathEase.Model.*;
import com.MathEase.MathEase.Repository.QuestionAttachmentRepository;
import com.MathEase.MathEase.Repository.UserAnswerRepository;
import com.MathEase.MathEase.Service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentQuizResultsController {

    @Autowired
    private MenuController menuController;

    @Autowired
    private TopicService topicService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private OptionService optionService;
    @Autowired
    private UserAnswerRepository userAnswerRepository;
    @Autowired
    private QuestionAttachmentRepository questionAttachmentRepository;

    @GetMapping("/student/congratulations")
    public String Congrats(HttpSession session, Model model) {

        // Check if user is logged in and is a student
        if (session.getAttribute("userId") == null || !session.getAttribute("role").equals("student")) {
            return "redirect:/login";
        }

        return "student/completion-page";
    }

    @GetMapping("/student/quiz")
    public String getQuiz(HttpSession session, Model model) {

        // Check if user is logged in and is a student
        if (session.getAttribute("userId") == null || !session.getAttribute("role").equals("student")) {
            return "redirect:/login";
        }

        // Get all topics
        List<Topic> topics = topicService.getAllTopics();
        model.addAttribute("topics", topics);
        menuController.setMenuBar(session, model);
        return "student/student-quiz-results";
    }

    @GetMapping("/api/questionsFetch/{topicId}")
    public ResponseEntity<List<StudentResult>> getQuizResults (HttpSession session,
                                                               Model model,
                                                               @PathVariable String topicId) {

        // Check if user is logged in and is a student
        if (session.getAttribute("userId") == null || !session.getAttribute("role").equals("student")) {
            return ResponseEntity.badRequest().build();
        }

        // Get all questions for the topic
        List<StudentResult> studentResults = new ArrayList<>();

        // Get the topic and user
        Topic topic = topicService.getTopicById(Long.parseLong(topicId));
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.getUserById(userId);

        // Get all answers for the user and topic
        List<UserAnswer> userAnswers = answerService.getAnswersByUserAndTopic(user, topic);

        // Get the question details
        for (UserAnswer userA: userAnswers) {
            // Get the question details
            StudentResult studentResult = new StudentResult();
            studentResult.setQuiz(userA.getQuestion().getQuestion());
            Questions questions = questionService.getQuestionById(userA.getQuestion().getQuestionId());
            studentResult.setQuestionId(questions.getQuestionId());
            // Get the correct and wrong options
            Options correctOptions = optionService.getCorrectOption(questions);
            studentResult.setCorrectOptions(correctOptions.getOption());
            // Get the wrong options
            List<Options> wrongOptions = optionService.getWrongOptions(questions);
            List<String> wrongOptionsList = new ArrayList<>();
            for (Options wrongOption: wrongOptions) {
                wrongOptionsList.add(wrongOption.getOption());
            }
            studentResult.setWrongOptions(wrongOptionsList);

            studentResult.setUserAnswer(userA.getOption().getOption());

            studentResults.add(studentResult);
        }

        return ResponseEntity.ok(studentResults);
    }



    @GetMapping("/api/questionStudent/{questionId}")
    public ResponseEntity<StudentResult> getQuestionsDetails (@PathVariable Long questionId,
                                                              HttpSession session){

        // Check if user is logged in and is a student
        Questions questions = questionService.getQuestionById(questionId);
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.getUserById(userId);

        // Get the user answer
        UserAnswer userAnswer = userAnswerRepository.findByUserAndQuestion(user, questions);

        // Get the question details
        StudentResult studentResult = new StudentResult();
        studentResult.setQuiz(questions.getQuestion());
        studentResult.setCorrectOptions(optionService.getCorrectOption(questions).getOption());
        studentResult.setWrongOptions(optionService.getWrongOptions(questions).stream().map(Options::getOption).toList());
        studentResult.setUserAnswer(userAnswer.getOption().getOption());

        // Get the attachment
        if (questionAttachmentRepository.existsByQuestion(questions)) {
            QuestionAttachment questionAttachment = questionAttachmentRepository.findByQuestion(questions);
            studentResult.setFilePath(questionAttachment.getAttachmentFilename());
        }

        return ResponseEntity.ok(studentResult);

    }

}
