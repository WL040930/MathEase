package com.MathEase.MathEase.Controller;

import com.MathEase.MathEase.Model.User;
import com.MathEase.MathEase.Repository.UserRepository;
import com.MathEase.MathEase.Service.UserService;
import com.MathEase.MathEase.Util.JsonParser;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class SettingsController {

    @Autowired
    private MenuController menuController;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/admin/settings")
    public String settings(HttpSession session, Model model) {

        menuController.setMenuBar(session, model);

        Long userId = (Long) session.getAttribute("userId");

        String username = userService.getUsername(userId);
        String role = userService.getRoleName(userId);
        String profilePicture = userService.getProfilePicture(userId);
        String email = userService.getEmail(userId);

        model.addAttribute("userId", userId);
        model.addAttribute("usernameInput", username);
        model.addAttribute("emailTitle", email);
        model.addAttribute("roleTitle", role);
        model.addAttribute("profilePicture", "/data/" + profilePicture);

        return "shared/settings";
    }

    @PostMapping("/update-username/{userId}")
    public ResponseEntity<String> updateUsername(@PathVariable Long userId, @RequestBody String newUsername) {
        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            newUsername = JsonParser.extractUsername(newUsername);
            user.setUsername(newUsername);
            userRepository.save(user);
            return ResponseEntity.ok("Username updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update username");
        }
    }


}
