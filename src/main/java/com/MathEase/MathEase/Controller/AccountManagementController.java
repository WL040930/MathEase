package com.MathEase.MathEase.Controller;

import com.MathEase.MathEase.Model.User;
import com.MathEase.MathEase.Model.UserDTO;
import com.MathEase.MathEase.Repository.UserRepository;
import com.MathEase.MathEase.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AccountManagementController {

    @Autowired
    private MenuController menuController;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/admin/account")
    public String account(HttpSession session,
                          Model model) {

        // Check if user is logged in and is an admin
        if (session.getAttribute("userId") == null || !session.getAttribute("role").equals("admin")) {
            return "redirect:/login";
        }

        // Get all users
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        menuController.setMenuBar(session, model);

        return "admin/admin-account";
    }

    @GetMapping("/api/users/search")
    public List<String> searchUsers(@RequestParam("query") String query) {
        return userService.findUsersByEmailContaining(query);
    }

    @PostMapping("/api/updateStatus")
    public ResponseEntity<String> updateStatus(@RequestParam("userId") Long userId,
                                               @RequestParam("newStatus") boolean status) {

        // Update user status
        userService.updateUserStatus(userId, status);
        return ResponseEntity.ok("User status updated successfully");
    }

    @PostMapping("/api/updateUser")
    public ResponseEntity<String> updateUser(@RequestParam("userId") Long userId,
                                             @RequestParam("username") String username,
                                             @RequestParam(value = "password", required = false) String password) {

        // Update user details
        User user = userRepository.findById(userId).get();
        user.setUsername(username);

        // Check if password is not empty
        if (!password.equals("")) {
            user.setPassword(password);
        }

        // Save user
        userRepository.save(user);
        return ResponseEntity.ok("User updated successfully");
    }


    @GetMapping("/api/getUsers")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<User> users = userRepository.findAll();

        // Map User entities to UserDTO objects
        List<UserDTO> userDTOs = users.stream()
                .map(this::mapUserToDTO) // Call mapUserToDTO method
                .collect(Collectors.toList());

        return ResponseEntity.ok(userDTOs);
    }

    private UserDTO mapUserToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setJoinedDate(user.getJoinedDate());
        dto.setActivated(user.isActivated());
        return dto;
    }


}
