package com.MathEase.MathEase.Controller;

import com.MathEase.MathEase.Model.User;
import com.MathEase.MathEase.Repository.UserRepository;
import com.MathEase.MathEase.Service.EmailService;
import com.MathEase.MathEase.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
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

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("logoutMessage", "You have been logged out");
        session.invalidate();
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        RedirectAttributes redirectAttributes,
                        HttpSession session) {

        boolean loginSuccess = userService.authenticateUser(email, password);

        if (loginSuccess) {
            User user = userRepository.findByEmail(email);

            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "User not found");
                return "redirect:/login"; // Redirect with error message
            }

            if (userService.isUserActivated(email)) {
                session.setAttribute("userId", user.getUserId());
                session.setAttribute("email", user.getEmail());
                session.setAttribute("username", user.getUsername());
                String roleName = user.getRole().getRoleName();
                session.setAttribute("role", roleName);

                if (roleName.equals("admin")) {
                    return "redirect:/admin/dashboard";
                } else {
                    return "redirect:/student/dashboard";
                }
                
            } else {
                // Generate activation token and send verification email
                String activationToken = UUID.randomUUID().toString();
                user.setActivationToken(activationToken);
                userRepository.save(user);

                emailService.sendVerificationEmail(user);

                redirectAttributes.addFlashAttribute("error", "Account not activated. Please check your email for activation link.");
                return "redirect:/login"; // Redirect with error message
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid email or password");
            return "redirect:/login"; // Redirect with error message
        }
    }
}
