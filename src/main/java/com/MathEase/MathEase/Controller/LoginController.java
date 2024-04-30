package com.MathEase.MathEase.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.MathEase.MathEase.Service.UserService;
import com.MathEase.MathEase.Model.User;
import com.MathEase.MathEase.Repository.UserRepository;
import com.MathEase.MathEase.Service.EmailService;
import java.util.UUID;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Return the login.html Thymeleaf template
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        boolean loginSuccess = userService.authenticateUser(email, password);

        if (loginSuccess) {
            User user = userRepository.findByEmail(email);

            if (user == null) {
                model.addAttribute("error", "User not Found");
                return "login"; // Return to login page with error message
            }

            if (userService.isUserActivated(email)) {
                model.addAttribute("user", user);
                return "redirect:/admin-dashboard"; // Return the admin-dashboard.html Thymeleaf template
            } else {
                String activationToken = UUID.randomUUID().toString();
                user.setActivationToken(activationToken);
                userRepository.save(user); // Update user with activation token

                // Send verification email
                emailService.sendVerificationEmail(user);

                model.addAttribute("error", "Account not activated. Please check your email for activation link.");
                return "login"; // Return to login page with error message
            }


        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login"; // Return to login page with error message
        }
    }
}
