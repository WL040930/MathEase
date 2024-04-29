package com.MathEase.MathEase.Service;

import com.MathEase.MathEase.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.MathEase.MathEase.Model.Role;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    public Role getRoleByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

}
