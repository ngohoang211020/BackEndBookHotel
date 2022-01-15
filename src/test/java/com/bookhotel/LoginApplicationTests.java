package com.bookhotel;

import com.bookhotel.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class LoginApplicationTests {
    @Autowired
    private UserRepository repo;

    @Test
    void contextLoads() {
    }

}
