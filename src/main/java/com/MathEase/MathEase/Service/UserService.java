package com.MathEase.MathEase.Service;

import com.MathEase.MathEase.Model.User;
import com.MathEase.MathEase.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // authenticate user
    public boolean authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            return true;
        }

        return false;
    }

    // activate user by token
    public User activateUserByToken(String token) {
        User user = userRepository.findByActivationToken(token);

        if (user != null) {
            user.setActivated(true);
            user.setActivationToken(null);
            userRepository.save(user);
        }

        return user;
    }

    // change password by reset token
    public User changePasswordByResetToken(String token, String password) {
        User user = userRepository.findByResetToken(token);

        if (user != null) {
            user.setPassword(password);
            user.setResetToken(null);
            userRepository.save(user);
        }

        return user;
    }

    // find user by reset token
    public User getUserByResetToken(String token) {
        return userRepository.findByResetToken(token);
    }

    // update user reset token
    public void updateUserPassword(User user, String password) {
        user.setPassword(password);
        userRepository.save(user);
    }

    public boolean isEmailTaken(String email) {
        User user = userRepository.findByEmail(email);
        return user != null;
    }

    // check if user is activated
    public boolean isUserActivated(String email) {
        User user = userRepository.findByEmail(email);
        return user != null && user.isActivated();
    }

    // check if the user is exists
    public boolean isUserExists(String email) {
        return userRepository.existsByEmail(email);
    }

    // get user by email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // get username by user id
    public String getUsername(Long userId) {
        User user = userRepository.findById(userId).get();
        return user.getUsername();
    }

    // get user role by email
    public String getRoleName(Long userId) {
        User user = userRepository.findById(userId).get();
        return user.getRole().getRoleName();
    }

    // get profile picture by user id
    public String getProfilePicture(Long userId) {
        User user = userRepository.findById(userId).get();
        return user.getProfilePicture();
    }

    // get email by user id
    public String getEmail(Long userId) {
        User user = userRepository.findById(userId).get();
        return user.getEmail();
    }

    // update user username
    public void updateUsername(Long userId, String newUsername) {
        userRepository.updateUsername(userId, newUsername);
    }

    // get joined date by user id
    public String getJoinedDate(Long userId) {
        User user = userRepository.findById(userId).get();
        return user.getJoinedDate();
    }

    // list of users by email
    public List<String> findUsersByEmailContaining(String query) {
        List<String> matchingEmails = userRepository.findByEmailContaining(query)
                .stream()
                .map(user -> user.getEmail())
                .collect(Collectors.toList());
        return matchingEmails;
    }

    // list of users by username
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // update user status
    public void updateUserStatus(Long userId, boolean status) {
        User user = userRepository.findById(userId).get();
        user.setActivated(status);
        userRepository.save(user);
    }

    // get user by id
    public User getUserById(Long userId) {
        return userRepository.findById(userId).get();
    }



}
