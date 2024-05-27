package com.MathEase.MathEase.Controller;

import com.MathEase.MathEase.Repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminDashboardController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuController MenuController;

    @GetMapping("/admin/dashboard")
    public String showAdminDashboard(HttpSession session,
                                     RedirectAttributes redirectAttributes,
                                     Model model) {

        // Check if user is logged in and is an admin
        if (!SessionController.validateAdmin(session)) {
            redirectAttributes.addAttribute("errorMessage", "unauthorized");
            return "redirect:/login";
        }

        // Set menu bar
        MenuController.setMenuBar(session, model);

        return "admin/admin-dashboard";
    }
}
