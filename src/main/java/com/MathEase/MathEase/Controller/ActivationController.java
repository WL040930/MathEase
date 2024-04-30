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
        User user = userService.activateUserByToken(token);
        if (user != null) {
            model.addAttribute("message", "Account activated successfully!");
            return "email_message/activation-success"; // Return the name of the success view
        } else {
            model.addAttribute("error", "Invalid activation token.");
            return "email_message/activation-error"; // Return the name of the error view
        }
    }
}
