package com.bookhotel.controller;

import com.bookhotel.entity.User;
import com.bookhotel.repository.RoomRepository;
import com.bookhotel.repository.UserRepository;
import com.bookhotel.request.SignupRequest;
import com.bookhotel.response.ResponseObject;
import com.bookhotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/api/v1/user")
public class UserController {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @PutMapping("/{userId}")
    public ResponseEntity<ResponseObject> updateUser(@PathVariable("userId") Integer userId, @RequestBody SignupRequest userInfo) {
        if(userService.existsById(userId)){
            User user=userService.findById(userId);
            user.setName(userInfo.getName());
            user.setPhone(userInfo.getPhone());
            user.setAddress(userInfo.getAddress());
            user.setIdentification(userInfo.getIdentification());
            user.setEmail(userInfo.getEmail());
            user.setPassword(encoder.encode(userInfo.getPassword()));
            userService.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query location successfully", user);
            );
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "User Not Found", "")
            );
        }
    }
}
