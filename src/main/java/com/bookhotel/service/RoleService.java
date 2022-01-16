package com.bookhotel.service;

import com.bookhotel.entity.Role;
import com.bookhotel.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role findById(Integer id) {
        return roleRepository.findById(id).get();
    }

    public void save(Role role) {
        roleRepository.save(role);
    }

    public Boolean existsById(Integer id) {
        return roleRepository.existsById(id);
    }

    public Boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }

    public void update(Role role, Integer id) {
        Role existedRole = roleRepository.findById(id).get();
        if (existedRole != null) {
            existedRole.setName(role.getName());
        }
        save(existedRole);
    }

    public void deleteById(Integer id) {
        roleRepository.deleteById(id);
    }

}
