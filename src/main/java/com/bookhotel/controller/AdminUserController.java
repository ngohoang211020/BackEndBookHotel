package com.bookhotel.controller;

import com.bookhotel.entity.Role;
import com.bookhotel.entity.User;
import com.bookhotel.enums.ERole;
import com.bookhotel.request.SignupRequest;
import com.bookhotel.response.MessageResponse;
import com.bookhotel.response.ResponseObject;
import com.bookhotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("api/admin")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<ResponseObject> listFirstPage() {
        return listByPage(1);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/{pageNum}")
    public ResponseEntity<ResponseObject> listByPage(@PathVariable("pageNum") Integer pageNum) {

        return !userService.listByPage(pageNum).getContent().isEmpty() ? ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Query USERS by page successfully", userService.listByPage(1))) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Cannot Find USERS", ""));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users")
    public ResponseEntity<ResponseObject> searchUser(@RequestParam("keyword") String keyword) {
        return userService.findByKeyWord(keyword).size()>0 ? ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Query USERS by keyword successfully", userService.findByKeyWord(keyword))) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Cannot Find USERS", ""));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users/save")
    public ResponseEntity<MessageResponse> insertUser(@RequestBody SignupRequest signupRequest) {
        if (userService.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!", false));
        }

        if (userService.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!", false));
        }
        User user = new User(signupRequest.getUsername(), signupRequest.getEmail(), passwordEncoder.encode(signupRequest.getPassword()), signupRequest.getName(), signupRequest.getGender(), signupRequest.getAddress(), signupRequest.getIdentification(), signupRequest.getPhone());
        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles.size() != 0) {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = userService.findByName(ERole.ROLE_ADMIN);
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = userService.findByName(ERole.ROLE_MODERATOR);
                        roles.add(modRole);

                        break;
                    case "user":
                        Role userRole = userService.findByName(ERole.ROLE_USER);
                        roles.add(userRole);

                        break;
                }
            });
        } else {
            Role userRole = userService.findByName(ERole.ROLE_USER);
            roles.add(userRole);
        }

        user.setRoles(roles);
        userService.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!", true));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{id}")
    public ResponseEntity<MessageResponse> updateUser(@RequestBody User user, @PathVariable("id") Integer id) {
        userService.update(user, id);
        return ResponseEntity.ok(new MessageResponse("User updated Succesfully!!!", true));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable("id") Integer id) {
        Boolean exists = userService.existsById(id);
        if (exists) {
            userService.deleteById(id);
            return ResponseEntity.ok(new MessageResponse("User deleted Succesfully!!!", true));

        } else {
            return ResponseEntity.ok(new MessageResponse("Deleted User fail!!!", false));
        }
    }


}
