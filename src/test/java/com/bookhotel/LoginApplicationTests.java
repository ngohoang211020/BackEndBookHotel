package com.bookhotel;

import com.bookhotel.repository.UserRepository;
import com.bookhotel.util.StringProccessUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;


@SpringBootTest
class LoginApplicationTests {
    @Autowired
    private UserRepository repo;

    @Test
    void contextLoads() {
        Date y=new Date("2022/2/23");
        System.out.println(y);
    }

}
