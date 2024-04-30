package com.MathEase.MathEase.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminDashboardController {

    @GetMapping("/admin-dashboard")
    public String showAdminDashboard() {
        return "admin-dashboard"; // Return the admin-dashboard.html Thymeleaf template
    }
}
