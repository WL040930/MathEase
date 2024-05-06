package com.MathEase.MathEase.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {

    @PostMapping("/api/topicAnswered")
    public ResponseEntity<?> isTopicFullyAnswered(@RequestParam Long topicId,
                                                  @RequestParam Long userId ) {

        return null;
    }

}
