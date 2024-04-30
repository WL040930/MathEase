package com.MathEase.MathEase.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentDashboardController {

    @GetMapping("student/dashboard") // This mapping should match the desired URL
    public String showAdminDashboard(HttpSession session, Model model) {
        // Check if userId attribute exists in session
        if (session.getAttribute("userId") != null) {
            // Retrieve userId from session
            Long userId = (Long) session.getAttribute("userId");

            // Pass userId to the view (student-admin-dashboard.html)
            model.addAttribute("userId", userId);

            // Return the correct view name for the Thymeleaf template
            return "student/student-dashboard"; // Specify the correct path to the Thymeleaf template
        } else {
            // If userId attribute is not found in session, redirect to login page
            return "redirect:/login";
        }
    }

}
