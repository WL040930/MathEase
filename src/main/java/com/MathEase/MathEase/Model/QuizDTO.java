package com.MathEase.MathEase.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuizDTO {

    private Long quizId;
    private String quiz;
    private String filePath;
    private List<Options> options;

}
