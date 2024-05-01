package com.MathEase.MathEase.Controller;

import com.MathEase.MathEase.Model.User;
import com.MathEase.MathEase.Repository.UserRepository;
import com.MathEase.MathEase.Service.EmailService;
import com.MathEase.MathEase.Service.RoleService;
import com.MathEase.MathEase.Util.FileNameUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Controller
public class RegisterController {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final EmailService emailService;
    private FileNameUtil fileNameUtil = new FileNameUtil();

    public RegisterController(UserRepository userRepository, RoleService roleService, EmailService emailService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.emailService = emailService;
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

        if (userRepository.findByEmail(email) != null) {
            model.addAttribute("error", "Email already exists");
            return "register";
        }

        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "register";
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(roleService.getRoleById(2L));
        user.setActivated(false);

        if (file != null && !file.isEmpty()) {
            String originalFileName = file.getOriginalFilename();
            assert originalFileName != null;

            String fileName = fileNameUtil.transferFile(file, fileNameUtil.UPLOAD_DIR);

            // Set the profile picture filename in the user object
            user.setProfilePicture(fileName);
        } else {
            user.setProfilePicture("default_profile_picture.jpg");
        }

        // Save the user to database
        User savedUser = userRepository.save(user);

        if (savedUser != null) {
            // Generate unique activation token
            String activationToken = UUID.randomUUID().toString();
            savedUser.setActivationToken(activationToken);
            userRepository.save(savedUser); // Update user with activation token

            // Send verification email
            emailService.sendVerificationEmail(savedUser);

            model.addAttribute("success", "User registered successfully! Please check your email to activate your account.");
            return "verification";
        } else {
            model.addAttribute("error", "Failed to register user!");
            return "register";
        }
    }
}