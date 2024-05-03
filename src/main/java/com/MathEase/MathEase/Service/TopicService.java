package com.MathEase.MathEase.Service;

import com.MathEase.MathEase.Repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.MathEase.MathEase.Model.Topic;
import java.util.List;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    // get topic sorted by topicID
    public List<Topic> getAllTopics() {
        return topicRepository.getAllTopics();
    }


}
