package com.MathEase.MathEase.Repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.MathEase.MathEase.Model.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long>{
}
