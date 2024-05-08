package com.MathEase.MathEase.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentResult {

    private String quiz;
    private String filePath;
    private String correctOptions;
    private List<String> wrongOptions;
    private String userAnswer;

}
