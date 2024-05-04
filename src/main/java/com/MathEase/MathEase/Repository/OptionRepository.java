package com.MathEase.MathEase.Repository;

import com.MathEase.MathEase.Model.Options;
import com.MathEase.MathEase.Model.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionRepository extends JpaRepository<Options, Long>{

    Options findByOptionId(Long optionId);

    List<Options> findByQuestion(Questions question);

}
