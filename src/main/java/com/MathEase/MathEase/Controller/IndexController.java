package com.MathEase.MathEase.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    // return home page
    @GetMapping("/")
    public String showHomePage(HttpSession session, Model model) {
        return "index/index";
    }

    // return problem page
    @GetMapping("/problem")
    public String showProblemPage() {
        return "index/problem";
    }

    // return solution page
    @GetMapping("/solution")
    public String showSolutionPage() {
        return "index/solution";
    }
}
