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

        // Check if user exists
        if (userService.isUserExists(email)) {

            // Generate a random token
            User user = userService.getUserByEmail(email);
            String token = UUID.randomUUID().toString();

            // Save the token to the user
            user.setResetToken(token);

            // Save the user
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

        // Check if token is missing
        if (token == null) {
            model.addAttribute("message", "Reset token is missing.");
            return "email_message/failed-reset-password";
        }

        // Check if token is valid
        User user = userService.getUserByResetToken(token);

        // Check if user exists
        if (user == null) {
            model.addAttribute("message", "Invalid reset token.");
            return "email_message/failed-reset-password";
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

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            model.addAttribute("message", "Passwords do not match.");
            return "email_message/reset-password";
        }

        // Check if token is valid
        User user = userService.getUserByResetToken(token);

        // Check if user exists
        if (user != null) {
            // Update the password
            userService.updateUserPassword(user, password);
            user.setResetToken(null);
            userRepository.save(user);

            redirectAttributes.addFlashAttribute("successMessage", "Your password has been updated. Please login with your new password.");
            return "redirect:/login";
        } else {
            model.addAttribute("message", "Invalid reset token.");
            return "forget-password";
        }
    }


}
