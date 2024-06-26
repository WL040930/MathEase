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

        // get all options for the question
        List<Options> options = optionRepository.findByQuestion(questions);
        for (Options option : options) {
            if (option.isCorrect()) {
                return option;
            }
        }
        return null;
    }

    public ArrayList<Options> getWrongOptions(Questions questions) {
        // get all wrong options for the question
        List<Options> options = optionRepository.findByQuestion(questions);
        ArrayList<Options> wrongOptions = new ArrayList<>();
        for (Options option : options) {
            if (!option.isCorrect()) {
                wrongOptions.add(option);
            }
        }
        return wrongOptions;
    }

    // get all options for a question
    public List<Options> getOptionsByQuestion(Questions questions) {
        return optionRepository.findByQuestion(questions);
    }

    // check if the answer is correct
    public boolean isAnswerCorrect(Long optionId) {
        Options option = optionRepository.findById(optionId).get();
        return option.isCorrect();
    }

    // get option by id
    public Options getOptionById(Long option) {
        return optionRepository.findById(option).get();
    }

}
