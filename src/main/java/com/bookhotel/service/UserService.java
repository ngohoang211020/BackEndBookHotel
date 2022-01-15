package com.bookhotel.service;

import com.bookhotel.entity.Role;
import com.bookhotel.entity.User;
import com.bookhotel.enums.ERole;
import com.bookhotel.repository.RoleRepository;
import com.bookhotel.repository.UserRepository;
import com.bookhotel.response.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    public static final int USERS_PER_PAGE = 5;

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public void save(User user) {
        userRepo.save(user);
    }


    public Boolean existsById(Integer id) {
        return userRepo.existsById(id);
    }


    public void update(User user, Integer id) {
        User existedUser = userRepo.findById(id).get();
        if (existedUser != null) {
            if (user.getPassword().isEmpty()) {
                user.setPassword(existedUser.getPassword());
            } else {
                encodePassword(user);
            }
            user.setId(id);
        }
        save(user);
    }


    public Paging<User> listByPage(Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, USERS_PER_PAGE);
        Page<User> userPage = userRepo.findAll(pageable);
        return new Paging<User>(userPage);
    }


    public void deleteById(Integer id) {
        userRepo.deleteById(id);
    }


    public List<User> findAll() {
        return userRepo.findAll();
    }


    public User findById(Integer id) {
        return userRepo.findById(id).get();
    }


    public User findByName(String name) {
        return userRepo.findByUsername(name).get();
    }


    public List<User> findByKeyWord(String keyword) {

        return userRepo.findByKeyWord(keyword);
    }

    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    public boolean existsByUsername(String username) {
        return userRepo.existsByUsername(username);
    }

    public Role findByName(ERole role) {
        return roleRepo.findByName(role)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    }


}

