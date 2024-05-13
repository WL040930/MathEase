package com.MathEase.MathEase.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultDTO {

    private String chapter;
    private String chapterName;
    private double score;

}
