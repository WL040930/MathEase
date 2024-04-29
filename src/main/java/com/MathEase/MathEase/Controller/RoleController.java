package com.MathEase.MathEase.Controller;

import com.MathEase.MathEase.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.MathEase.MathEase.Model.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleRepository roleRespository;

    @Autowired
    public RoleController(RoleRepository roleRespository) {
        this.roleRespository = roleRespository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        Role role = roleRespository.findById(id).orElse(null);
        if (role == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(role);
    }

}
