package com.MathEase.MathEase.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.MathEase.MathEase.Model.Topic;
import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long>{

    // sort by topicID
    @Query("SELECT t FROM Topic t ORDER BY t.topicId ASC")
    List<Topic> getAllTopics();

    Topic getTopicByTopicId(Long topicId);

    long count();
}
