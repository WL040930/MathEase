package com.MathEase.MathEase.Service;

import com.MathEase.MathEase.Model.Options;
import com.MathEase.MathEase.Model.Questions;
import com.MathEase.MathEase.Repository.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class OptionService {

    @Autowired
    private OptionRepository optionRepository;

    public Options getCorrectOption(Questions questions) {

        List<Options> options = optionRepository.findByQuestion(questions);
        for (Options option : options) {
            if (option.isCorrect()) {
                return option;
            }
        }
        return null;
    }

    public ArrayList<Options> getWrongOptions(Questions questions) {
        List<Options> options = optionRepository.findByQuestion(questions);
        ArrayList<Options> wrongOptions = new ArrayList<>();
        for (Options option : options) {
            if (!option.isCorrect()) {
                wrongOptions.add(option);
            }
        }
        return wrongOptions;
    }

    public List<Options> getOptionsByQuestion(Questions questions) {
        return optionRepository.findByQuestion(questions);
    }

}
