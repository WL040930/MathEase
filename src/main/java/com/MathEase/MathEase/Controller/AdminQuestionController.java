package com.MathEase.MathEase.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminQuestionController {

    @Autowired
    private MenuController menuController;

    @GetMapping("/admin/questions")
    public String getAdminQuestions(HttpSession session, Model model) {

        if (session.getAttribute("userId") == null || !session.getAttribute("role").equals("admin")) {
            return "redirect:/login";
        }

        menuController.setMenuBar(session, model);

        return "admin/admin-questions";
    }
}
