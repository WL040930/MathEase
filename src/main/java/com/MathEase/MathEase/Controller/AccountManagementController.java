package com.MathEase.MathEase.Controller;

import com.MathEase.MathEase.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AccountManagementController {

    @Autowired
    private MenuController menuController;

    @Autowired
    private UserService userService;

    @GetMapping("/admin/account")
    public String account(HttpSession session, Model model) {

        if (session.getAttribute("userId") == null || !session.getAttribute("role").equals("admin")) {
            return "redirect:/login";
        }

        menuController.setMenuBar(session, model);

        return "admin/admin-account";
    }

    @GetMapping("/api/users/search")
    public List<String> searchUsers(@RequestParam("query") String query) {
        return userService.findUsersByEmailContaining(query);
    }

}
