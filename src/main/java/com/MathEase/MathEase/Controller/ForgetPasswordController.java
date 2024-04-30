package com.MathEase.MathEase.Controller;

import com.MathEase.MathEase.Model.User;
import com.MathEase.MathEase.Repository.UserRepository;
import com.MathEase.MathEase.Service.EmailService;
import com.MathEase.MathEase.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
public class ForgetPasswordController {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final UserService userService;

    @Autowired
    public ForgetPasswordController(EmailService emailService, UserService userService, UserRepository userRepository) {
        this.emailService = emailService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/forget-password")
    public String forgetPassword() {
        return "forget-password";
    }

    @PostMapping("/forget-password")
    public String sendEmail(@RequestParam("email") String email, Model model) {

        if (userService.isUserExists(email)) {
            User user = userService.getUserByEmail(email);
            String token = UUID.randomUUID().toString();
            user.setResetToken(token);

            userRepository.save(user);

            emailService.sendForgetPassword(user);

            model.addAttribute("message", "Password reset link has been sent to your email.");
            return "forget-password";
        } else {
            model.addAttribute("message", "User with this email does not exist.");
            return "forget-password";
        }
    }

    @GetMapping("/reset-password")
    public String resetPassword(@RequestParam(value = "token", required = false) String token, Model model) {
        if (token == null) {
            model.addAttribute("message", "Reset token is missing.");
            return "email_message/failed-reset-password"; // Redirect to an error page
        }

        User user = userService.getUserByResetToken(token);
        if (user == null) {
            // Handle invalid token scenario
            model.addAttribute("message", "Invalid reset token.");
            return "email_message/failed-reset-password"; // Redirect to an error page
        }

        model.addAttribute("token", token);
        return "email_message/reset-password";
    }


    @PostMapping("/reset-password")
    public String updatePassword(@RequestParam("token") String token,
                                 @RequestParam("password") String password,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {

        if (!password.equals(confirmPassword)) {
            model.addAttribute("message", "Passwords do not match.");
            return "email_message/reset-password";
        }

        User user = userService.getUserByResetToken(token);

        if (user != null) {
            // Update user's password
            userService.updateUserPassword(user, password);

            // Clear the reset token after password update
            user.setResetToken(null);
            userRepository.save(user);

            redirectAttributes.addFlashAttribute("successMessage", "Your password has been updated. Please login with your new password.");
            return "redirect:/login"; // Redirect to login page after password reset
        } else {
            model.addAttribute("message", "Invalid reset token.");
            return "forget-password";
        }
    }


}
