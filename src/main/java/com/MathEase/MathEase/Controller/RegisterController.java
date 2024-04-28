package com.MathEase.MathEase.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import com.MathEase.MathEase.Model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class RegisterController {

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    private static final String uploadDirectory = "src/main/resources/static/data/";

    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password,
                               @RequestParam("file") MultipartFile file,
                               Model model) {

        // Save the uploaded file
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(uploadDirectory + file.getOriginalFilename());
                Files.write(path, bytes);
                model.addAttribute("message", "File uploaded successfully!");
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("message", "Failed to upload file!");
            }
        } else {
            model.addAttribute("message", "No file uploaded!");
        }

        return "sendEmail";
    }
}