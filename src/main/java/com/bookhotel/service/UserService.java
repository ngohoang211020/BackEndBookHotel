package com.bookhotel.service;

import com.bookhotel.entity.Role;
import com.bookhotel.entity.User;
import com.bookhotel.enums.ERole;
import com.bookhotel.repository.RoleRepository;
import com.bookhotel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;

    public void save(User user) {
        userRepo.save(user);
    }

    public Role findByName(ERole role) {
        return roleRepo.findByName(role)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    }

    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    public boolean existsByUsername(String username) {
        return userRepo.existsByUsername(username);
    }
}
