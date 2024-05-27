package com.MathEase.MathEase.Controller;

import com.MathEase.MathEase.Model.Options;
import com.MathEase.MathEase.Model.QuestionAttachment;
import com.MathEase.MathEase.Model.Questions;
import com.MathEase.MathEase.Model.Topic;
import com.MathEase.MathEase.Repository.OptionRepository;
import com.MathEase.MathEase.Repository.QuestionAttachmentRepository;
import com.MathEase.MathEase.Repository.QuestionRepository;
import com.MathEase.MathEase.Service.OptionService;
import com.MathEase.MathEase.Util.FileNameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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
    @Autowired
    private OptionService optionService;

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
            // Save question
            Topic topic = new Topic();
            topic.setTopicId(topicId);

            // Save question
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

            // Save picture
            if (pictureFile != null && !pictureFile.isEmpty()) {
                String fileName = fileNameUtil.transferFile(pictureFile, fileNameUtil.UPLOAD_DIR);
                System.out.println(fileName);
                QuestionAttachment qa = new QuestionAttachment();
                qa.setAttachmentFilename(fileName);
                qa.setQuestion(question);
                questionAttachmentRepository.save(qa);
            }

            // Add success message
            redirectAttributes.addFlashAttribute("successMessage", "Question added successfully");
            return ResponseEntity.ok("Item added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add item");
        }
    }

    @PostMapping("/addQuestion")
    public ResponseEntity<String> editData(@RequestParam("questionId") Long questionId,
                                           @RequestParam("questionText") String questionText,
                                           @RequestParam("correctAnswer") String correctAnswer,
                                           @RequestParam("wrongAnswer1") String wrongAnswer1,
                                           @RequestParam("wrongAnswer2") String wrongAnswer2,
                                           @RequestParam("wrongAnswer3") String wrongAnswer3,
                                           @RequestParam(value = "picture", required = false) MultipartFile pictureFile){

        try {
            // Save question
            Questions question = questionRepository.findByQuestionId(questionId);
            question.setQuestion(questionText);
            questionRepository.save(question);

            // Save options
            Options correctOptions =  optionService.getCorrectOption(question);
            correctOptions.setOption(correctAnswer);
            optionRepository.save(correctOptions);

            // Save wrong options
            List<Options> wrongOptions = optionService.getWrongOptions(question);
            wrongOptions.get(0).setOption(wrongAnswer1);
            wrongOptions.get(1).setOption(wrongAnswer2);
            wrongOptions.get(2).setOption(wrongAnswer3);

            optionRepository.saveAll(wrongOptions);

            // Save picture
            if (pictureFile != null && !pictureFile.isEmpty()) {
                if (questionAttachmentRepository.existsByQuestion(question)) {
                    QuestionAttachment qa = questionAttachmentRepository.findByQuestion(question);
                    String fileName = fileNameUtil.transferFile(pictureFile, fileNameUtil.UPLOAD_DIR);
                    qa.setAttachmentFilename(fileName);
                    questionAttachmentRepository.save(qa);
                } else {
                    String fileName = fileNameUtil.transferFile(pictureFile, fileNameUtil.UPLOAD_DIR);
                    QuestionAttachment qa = new QuestionAttachment();
                    qa.setAttachmentFilename(fileName);
                    qa.setQuestion(question);
                    questionAttachmentRepository.save(qa);
                }
            }

            return ResponseEntity.ok("Question added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add item");
        }
    }
}
