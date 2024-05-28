package com.MathEase.MathEase.Repository;

import com.MathEase.MathEase.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by userId
    User findByUserId(Long userId);

    // Find user by email
    User findByEmail(String email);

    // find user by activation token
    User findByActivationToken(String token);

    // find user by email and activated
    User existsByEmailAndActivated (String email, boolean activated);

    // find user by email
    boolean existsByEmail(String email);

    // find user by reset token
    User findByResetToken(String token);

    // query to update user password
    @Modifying
    @Query("UPDATE User u SET u.username = :newUsername WHERE u.userId = :userId")
    void updateUsername(Long userId, String newUsername);

    // list of users by email
    List<User> findByEmailContaining(String email);

    // find total number of record
    long count();

}
