package com.MathEase.MathEase.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.MathEase.MathEase.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByActivationToken(String token);

    User existsByEmailAndActivated (String email, boolean activated);

    boolean existsByEmail(String email);

    User findByResetToken(String token);
}
