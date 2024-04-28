package com.MathEase.MathEase.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.MathEase.MathEase.Model.User;
import com.MathEase.MathEase.Repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            // Password matches, user authenticated
            return true;
        }

        // Invalid credentials
        return false;
    }
}
