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
            return true;
        }

        // Invalid credentials
        return false;
    }

    public User activateUserByToken(String token) {
        User user = userRepository.findByActivationToken(token);

        if (user != null) {
            user.setActivated(true);
            user.setActivationToken(null);
            userRepository.save(user);
        }

        return user;
    }

    public User changePasswordByResetToken(String token, String password) {
        User user = userRepository.findByResetToken(token);

        if (user != null) {
            user.setPassword(password);
            user.setResetToken(null);
            userRepository.save(user);
        }

        return user;
    }

    public User getUserByResetToken(String token) {
        return userRepository.findByResetToken(token);
    }

    public void updateUserPassword(User user, String password) {
        user.setPassword(password);
        userRepository.save(user);
    }

    public boolean isEmailTaken(String email) {
        User user = userRepository.findByEmail(email);
        return user != null;
    }

    public boolean isUserActivated(String email) {
        User user = userRepository.findByEmail(email);
        return user != null && user.isActivated();
    }

    public boolean isUserExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
