package com.MathEase.MathEase.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentDashboardController {

    private final MenuController menuController;

    public StudentDashboardController(MenuController menuController) {
        this.menuController = menuController;
    }

    @GetMapping("student/dashboard")
    public String showAdminDashboard(HttpSession session, Model model) {


        if (session.getAttribute("userId") == null) {
            return "redirect:/login?error=Unauthorized";
        }

        menuController.setMenuBar(session, model);

        return "student/student-dashboard";
    }

}
