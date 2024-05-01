package com.MathEase.MathEase.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminDashboardController {

    @GetMapping("/admin/dashboard")
    public String showAdminDashboard(HttpSession session, RedirectAttributes redirectAttributes) {
        if (!SessionController.validateAdmin(session)) {
            redirectAttributes.addAttribute("errorMessage", "unauthorized");
            return "redirect:/login";
        }
        return "menu/menu-admin";
    }
}
