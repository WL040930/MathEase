package com.MathEase.MathEase.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;

@Controller
public class SessionController {

    public static boolean validateAdmin(HttpSession session) {
        Object role = session.getAttribute("role");
        if (role == null || !role.equals("admin")) {
            session.invalidate();
            return false;
        } else {
            return true;
        }
    }

    public static boolean validateStudent(HttpSession session) {
        Object role = session.getAttribute("role");
        if (role == null || !role.equals("student")) {
            session.invalidate();
            return false;
        } else {
            return true;
        }
    }
}
