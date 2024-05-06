package com.MathEase.MathEase.Controller;

import com.MathEase.MathEase.Model.User;
import com.MathEase.MathEase.Repository.UserRepository;
import com.MathEase.MathEase.Service.UserService;
import com.MathEase.MathEase.Util.FileNameUtil;
import com.MathEase.MathEase.Util.JsonParser;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SettingsController {

    @Autowired
    private MenuController menuController;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private final FileNameUtil fileNameUtil = new FileNameUtil();

    @GetMapping({"/admin/settings", "/student/settings"})
    public String settings(HttpSession session, Model model) {

        if (session.getAttribute("userId") == null) {
            return "redirect:/login";
        }

        menuController.setMenuBar(session, model);

        Long userId = (Long) session.getAttribute("userId");

        String username = userService.getUsername(userId);
        String role = userService.getRoleName(userId);
        String profilePicture = userService.getProfilePicture(userId);
        String email = userService.getEmail(userId);
        String joinedDate = userService.getJoinedDate(userId);

        model.addAttribute("userId", userId);
        model.addAttribute("usernameInput", username);
        model.addAttribute("emailTitle", email);
        model.addAttribute("roleTitle", role);
        model.addAttribute("joinedDateInput", joinedDate);
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

    @PostMapping("/update-password/{userId}")
    public ResponseEntity<String> updatePassword(@PathVariable Long userId, @RequestBody String newPassword) {
        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            newPassword = JsonParser.extractPassword(newPassword);
            user.setPassword(newPassword);
            userRepository.save(user);

            return ResponseEntity.ok("Password updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update password");
        }
    }

    @PostMapping("/upload-profile-picture/{userId}")
    public ResponseEntity<String> uploadProfilePicture(@PathVariable Long userId,
                                                       @RequestParam("profilePicture") MultipartFile file,
                                                       Model model,
                                                       HttpSession session,
                                                       RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file to upload");
        }

        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            String fileName = fileNameUtil.transferFile(file, fileNameUtil.UPLOAD_DIR);
            user.setProfilePicture(fileName);
            userRepository.save(user);
            return ResponseEntity.ok("Profile picture uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload profile picture");
        }
    }

}
