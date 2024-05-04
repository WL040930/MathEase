package com.MathEase.MathEase.Repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.MathEase.MathEase.Model.Options;

@Repository
public interface OptionRepository extends JpaRepository<Options, Long>{

    Options findByOptionId(Long optionId);

}
