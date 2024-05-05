package com.MathEase.MathEase.Controller;

import com.MathEase.MathEase.Model.User;
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

@Controller
public class AccountManagementController {

    @Autowired
    private MenuController menuController;

    @Autowired
    private UserService userService;

    @GetMapping("/admin/account")
    public String account(HttpSession session,
                          Model model) {

        if (session.getAttribute("userId") == null || !session.getAttribute("role").equals("admin")) {
            return "redirect:/login";
        }

        List<User> users = userService.getAllUsers(); // Assuming a method to fetch all users
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

        System.out.println(status);
        userService.updateUserStatus(userId, status);
        return ResponseEntity.ok("User status updated successfully");
    }

}
