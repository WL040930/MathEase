package com.MathEase.MathEase.Repository;

import com.MathEase.MathEase.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserId(Long userId);

    User findByEmail(String email);

    User findByActivationToken(String token);

    User existsByEmailAndActivated (String email, boolean activated);

    boolean existsByEmail(String email);

    User findByResetToken(String token);

    @Modifying
    @Query("UPDATE User u SET u.username = :newUsername WHERE u.userId = :userId")
    void updateUsername(Long userId, String newUsername);

    List<User> findByEmailContaining(String email);
}
