package com.MathEase.MathEase.Controller;

import com.MathEase.MathEase.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SettingsController {

    @Autowired
    private MenuController menuController;

    @Autowired
    private UserService userService;

    @GetMapping("/admin/settings")
    public String settings(HttpSession session, Model model) {

        menuController.setMenuBar(session, model);

        Long userId = (Long) session.getAttribute("userId");

        String username = userService.getUsername(userId);
        String role = userService.getRoleName(userId);
        String profilePicture = userService.getProfilePicture(userId);
        String email = userService.getEmail(userId);

        model.addAttribute("usernameTitle", username);
        model.addAttribute("emailTitle", email);
        model.addAttribute("roleTitle", role);
        model.addAttribute("profilePicture", "/data/" + profilePicture);

        return "shared/settings";
    }

}
