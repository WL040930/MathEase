package com.MathEase.MathEase.Controller;

import com.MathEase.MathEase.Model.Options;
import com.MathEase.MathEase.Model.QuestionAttachment;
import com.MathEase.MathEase.Model.Questions;
import com.MathEase.MathEase.Model.Topic;
import com.MathEase.MathEase.Repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.MathEase.MathEase.Repository.OptionRepository;
import com.MathEase.MathEase.Util.FileNameUtil;
import com.MathEase.MathEase.Repository.QuestionAttachmentRepository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/api")
public class QuestionUploadController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private QuestionAttachmentRepository questionAttachmentRepository;

    private final FileNameUtil fileNameUtil = new FileNameUtil();

    @PostMapping("/addItem")
    public ResponseEntity<String> addItem(
            @RequestParam("questionText") String questionText,
            @RequestParam("correctAnswer") String correctAnswer,
            @RequestParam("wrongAnswer1") String wrongAnswer1,
            @RequestParam("wrongAnswer2") String wrongAnswer2,
            @RequestParam("wrongAnswer3") String wrongAnswer3,
            @RequestParam("topicId") Long topicId,
            @RequestParam(value = "picture", required = false) MultipartFile pictureFile,
            RedirectAttributes redirectAttributes) {
        try {
            Topic topic = new Topic();
            topic.setTopicId(topicId);

            Questions question = new Questions();
            question.setQuestion(questionText);
            question.setTopicId(topic);
            questionRepository.save(question);

            Long questionId = question.getQuestionId();

            Options correctOption = new Options();
            correctOption.setQuestion(question);
            correctOption.setOption(correctAnswer);
            correctOption.setCorrect(true);
            optionRepository.save(correctOption);

            Options wrongOption1 = new Options();
            wrongOption1.setQuestion(question);
            wrongOption1.setOption(wrongAnswer1);
            wrongOption1.setCorrect(false);
            optionRepository.save(wrongOption1);

            Options wrongOption2 = new Options();
            wrongOption2.setQuestion(question);
            wrongOption2.setOption(wrongAnswer2);
            wrongOption2.setCorrect(false);
            optionRepository.save(wrongOption2);

            Options wrongOption3 = new Options();
            wrongOption3.setQuestion(question);
            wrongOption3.setOption(wrongAnswer3);
            wrongOption3.setCorrect(false);
            optionRepository.save(wrongOption3);

            if (pictureFile != null && !pictureFile.isEmpty()) {
                String fileName = fileNameUtil.transferFile(pictureFile, fileNameUtil.UPLOAD_DIR);
                System.out.println(fileName);
                QuestionAttachment qa = new QuestionAttachment();
                qa.setAttachmentFilename(fileName);
                qa.setQuestion(question);
                questionAttachmentRepository.save(qa);
            }

            redirectAttributes.addFlashAttribute("successMessage", "Question added successfully");
            return ResponseEntity.ok("Item added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add item");
        }
    }

    @PostMapping("/editItem")
    public ResponseEntity<String> editItem(
            @RequestParam("questionId") Long questionId,
            @RequestParam("question") String questionText,
            @RequestParam("correctAnswer") String correctAnswer,
            @RequestParam("wrongAnswer1") String wrongAnswer1,
            @RequestParam("wrongAnswer2") String wrongAnswer2,
            @RequestParam("wrongAnswer3") String wrongAnswer3,
            @RequestParam(value = "picture", required = false) MultipartFile pictureFile) {

        try {
            System.out.println("Question ID: " + questionId);
            return ResponseEntity.ok("Item edited successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update question");
        }
    }



}
