package com.MathEase.MathEase.Controller;

import com.MathEase.MathEase.Model.User;
import com.MathEase.MathEase.Repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/admin-menu")
    public String showMenuPage(HttpSession session, Model model) {
        Object role = session.getAttribute("role");
        if (!SessionController.validateAdmin(session)) {
            return "redirect:/login?error=Unauthorized";
        }

        // Get user information based on session
        Long userId = (Long) session.getAttribute("userId");
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            model.addAttribute("username", user.getUsername());
            model.addAttribute("profilePictureUrl", "/data/" + user.getProfilePicture());

        }

        return "menu/menu-admin";
    }

    public void setMenuBar(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            model.addAttribute("menuUsername", user.getUsername());
            model.addAttribute("menuProfilePictureUrl", "/data/" + user.getProfilePicture());
            model.addAttribute("menuRole", session.getAttribute("role"));
        }
    }

}
