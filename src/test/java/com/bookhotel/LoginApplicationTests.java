package com.bookhotel;

import com.bookhotel.entity.RoomOrder;
import com.bookhotel.repository.RoomOrderRepository;
import com.bookhotel.repository.UserRepository;
import com.bookhotel.util.StringProccessUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;


@SpringBootTest
class LoginApplicationTests {
    @Autowired
    private RoomOrderRepository repo;

    @Test
    void contextLoads() {
        System.out.println(StringProccessUtil.DateToString(repo.findById(1).get().getArrival_date()));
    }

}
