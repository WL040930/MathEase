package com.MathEase.MathEase.Controller;

import com.MathEase.MathEase.Model.User;
import com.MathEase.MathEase.Repository.UserRepository;
import com.MathEase.MathEase.Service.EmailService;
import com.MathEase.MathEase.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        // add a flash attribute to display a message on the login page
        redirectAttributes.addFlashAttribute("logoutMessage", "You have been logged out");
        session.invalidate(); // invalidate the session
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        RedirectAttributes redirectAttributes,
                        HttpSession session) {

        // authenticate the user
        boolean loginSuccess = userService.authenticateUser(email, password);

        if (loginSuccess) {
            // get the user from the database
            User user = userRepository.findByEmail(email);

            // check if the user exists
            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "User not found");
                return "redirect:/login";
            }

            // check if the user is activated
            if (userService.isUserActivated(email)) {
                // set the session attributes
                session.setAttribute("userId", user.getUserId());
                session.setAttribute("email", user.getEmail());
                session.setAttribute("username", user.getUsername());
                String roleName = user.getRole().getRoleName();
                session.setAttribute("role", roleName);

                // redirect the user based on their role
                if (roleName.equals("admin")) {
                    return "redirect:/admin/dashboard";
                } else {
                    return "redirect:/student/dashboard";
                }
                
            } else {
                // generate an activation token
                String activationToken = UUID.randomUUID().toString();
                user.setActivationToken(activationToken);
                userRepository.save(user);

                emailService.sendVerificationEmail(user);

                redirectAttributes.addFlashAttribute("error", "Account not activated. Please check your email for activation link.");
                return "redirect:/login";
            }
        } else {
            // add a flash attribute to display an error message on the login page
            redirectAttributes.addFlashAttribute("error", "Invalid email or password");
            return "redirect:/login";
        }
    }
}
