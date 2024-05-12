package com.MathEase.MathEase.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminResultsDTO {

    private int totalAnswers;
    private int correctAnswers;
    private int incorrectAnswers;
    private int totalUsers;
    private double averageScore;
    private int maxScore;
    private int minScore;

}
