package com.MathEase.MathEase.Controller;

import com.MathEase.MathEase.Model.User;
import com.MathEase.MathEase.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ActivationController {

    @Autowired
    private UserService userService;

    @GetMapping("/activate")
    public String activateAccount(@RequestParam("token") String token, Model model) {

        // Activate user account
        User user = userService.activateUserByToken(token);

        // Check if user is activated
        if (user != null) {
            // Display success message
            model.addAttribute("message", "Account activated successfully!");
            return "email_message/activation-success";
        } else {
            // Display error message
            model.addAttribute("error", "Invalid activation token.");
            return "email_message/activation-error";
        }
    }
}
