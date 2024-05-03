package com.MathEase.MathEase.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminCourseController {

    @Autowired
    private MenuController menuController;

    @GetMapping("/admin/courses")
    public String showAdminCourses(HttpSession session, Model model) {

        if (session.getAttribute("userId") == null || !session.getAttribute("role").equals("admin")) {
            return "redirect:/admin/login";
        }

        menuController.setMenuBar(session, model);
        return "admin/admin-courses";
    }

}
