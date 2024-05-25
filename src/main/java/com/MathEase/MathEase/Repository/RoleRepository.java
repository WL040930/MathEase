package com.MathEase.MathEase.Repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.MathEase.MathEase.Model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

    // Find role by roleName
    Role findByRoleName(String role);

    // Find role by roleId
    Role findByRoleId(Long roleId);

}
