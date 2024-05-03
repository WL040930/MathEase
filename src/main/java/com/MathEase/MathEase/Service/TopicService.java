package com.MathEase.MathEase.Service;

import com.MathEase.MathEase.Model.Topic;
import com.MathEase.MathEase.Repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    // get topic sorted by topicID
    public List<Topic> getAllTopics() {
        return topicRepository.getAllTopics();
    }

    public Topic getTopicById(Long topicId) {
        Optional<Topic> topicOptional = topicRepository.findById(topicId);
        return topicOptional.orElse(null);
    }

}
