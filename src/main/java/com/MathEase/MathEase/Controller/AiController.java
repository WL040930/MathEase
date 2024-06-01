package com.MathEase.MathEase.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AiController {

    @GetMapping("/chat-model")
    public String chatModel() {
        return "shared/ai";
    }

}
