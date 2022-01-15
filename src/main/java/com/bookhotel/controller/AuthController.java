package com.bookhotel.controller;

import com.bookhotel.entity.Role;
import com.bookhotel.entity.User;
import com.bookhotel.enums.ERole;
import com.bookhotel.request.LoginRequest;
import com.bookhotel.request.SignupRequest;
import com.bookhotel.response.JwtResponse;
import com.bookhotel.response.MessageResponse;
import com.bookhotel.security.jwt.JwtUtils;
import com.bookhotel.security.service.UserDetailsImpl;
import com.bookhotel.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Operation(summary = "Đăng nhập", description = "Trả về jwt và thông tin user", tags = { "User" })
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        // Xác thực từ username và password.
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            //Set chuỗi authentication đó cho UserPrincipal
            // Nếu không xảy ra exception tức là thông tin hợp lệ
            // Set thông tin authentication vào Security Context
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // Trả về jwt cho người dùng.
            String jwt = jwtUtils.generateJwtToken(authentication);// Tạo ra jwt từ chuỗi authentication

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();//lay thong tin user
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles));
        } catch (AuthenticationException e) {
            return ResponseEntity.ok(new MessageResponse("Error: Authentication Fail", false));

        }

    }

    @Operation(summary = "Đăng ký", description = "Trả về message", tags = { "User" })
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!", false));
        }

        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!", false));
        }
        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),signUpRequest.getName(),signUpRequest.getGender(), signUpRequest.getAddress(), signUpRequest.getIdentification(), signUpRequest.getPhone());

        Set<String> strRoles = signUpRequest.getRole();
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
}
