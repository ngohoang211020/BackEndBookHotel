package com.bookhotel;

import com.bookhotel.entity.Hotel;
import com.bookhotel.entity.Role;
import com.bookhotel.enums.ERole;
import com.bookhotel.repository.HotelRepository;
import com.bookhotel.repository.RoleRepository;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class LoginApplicationTests {
	@Autowired
	private HotelRepository repo;
	@Test
	void contextLoads() {
		List<Hotel> listHotel=repo.findByLocation_Name("Da Nang");
		System.out.println(listHotel.get(0).getName());
	}

}
