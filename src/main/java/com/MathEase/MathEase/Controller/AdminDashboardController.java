package com.MathEase.MathEase.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminDashboardController {

    @GetMapping("/admin/dashboard")
    public String showAdminDashboard(HttpSession session) {
        Object role = session.getAttribute("role");
        if (!SessionController.validateAdmin(session)) {
            return "redirect:/login?error=Unauthorized";
        }
        return "admin/admin-dashboard";
    }
}
