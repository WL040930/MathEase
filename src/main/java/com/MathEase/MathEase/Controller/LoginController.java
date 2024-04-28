package com.MathEase.MathEase.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.MathEase.MathEase.Service.UserService;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Return the login.html Thymeleaf template
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        boolean loginSuccess = userService.authenticateUser(email, password);

        if (loginSuccess) {
            return "redirect:/"; // Redirect to the root URL (index.html) upon successful login
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login"; // Return to login page with error message
        }
    }
}
