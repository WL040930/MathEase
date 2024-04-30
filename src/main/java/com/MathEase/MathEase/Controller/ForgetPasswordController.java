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



}
