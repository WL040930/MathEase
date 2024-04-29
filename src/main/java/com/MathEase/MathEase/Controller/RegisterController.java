package com.MathEase.MathEase.Controller;

import com.MathEase.MathEase.Repository.UserRepository;
import com.MathEase.MathEase.Storage.DataStorage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import com.MathEase.MathEase.Model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.MathEase.MathEase.Service.RoleService;
import com.MathEase.MathEase.Model.Role;

import java.io.IOException;

@Controller
public class RegisterController {

    private final UserRepository userRepository;
    private final RoleService roleService;

    public RegisterController(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password,
                               @RequestParam("confirmPassword") String confirmPassword,
                               @RequestParam("file") MultipartFile file,
                               Model model) throws IOException {

        User user = new User();
        Role studentRole = roleService.getRoleByRoleName("student");

        if (userRepository.findByEmail(email) != null) {
            model.addAttribute("error", "Email already exists");
            return "register";
        }

        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "register";
        }

        if (file.isEmpty()) {
            user.setProfilePicture("default.jpg");
        } else {
            user.setProfilePicture(DataStorage.saveFile(file, DataStorage.UPLOAD_DIRECTORY));
        }

        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setActivated(false);
        user.setRole(studentRole);

        User savedUser = userRepository.save(user);

        if (savedUser != null && savedUser.getUserId() != null) {
            model.addAttribute("success", "User registered successfully!");
            return "verification";
        } else {
            model.addAttribute("error", "Failed to register user!");
            return "register";
        }

    }
}