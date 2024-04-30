package com.MathEase.MathEase.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForgetPasswordController {

    @GetMapping("/forget-password")
    public String forgetPassword() {
        return "forget-password";
    }

}
