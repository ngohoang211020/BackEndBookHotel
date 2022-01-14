package com.bookhotel;

import com.bookhotel.entity.User;
import com.bookhotel.response.Paging;
import com.bookhotel.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoginApplicationTests {
    @Autowired
    private UserService service;

    @Test
    void contextLoads() {
        Paging<User> paging=service.findByKeyWord("hoang",1);
        System.out.println(paging.getTotalElements());
    }
}


