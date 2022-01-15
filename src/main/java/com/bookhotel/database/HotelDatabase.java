package com.bookhotel.database;

import com.bookhotel.entity.Location;
import com.bookhotel.entity.Role;
import com.bookhotel.enums.ERole;
import com.bookhotel.repository.LocationRepository;
import com.bookhotel.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class HotelDatabase {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private LocationRepository locationRepository;
   /* @Bean
    CommandLineRunner initDatabase() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Role roleAdmin  = new Role(1, ERole.ROLE_ADMIN);
                Role roleMod=new Role(2,ERole.ROLE_MODERATOR);
                Role roleUser = new Role(3, ERole.ROLE_USER);
                List<Role> list=new ArrayList<Role>();
                list.add(roleAdmin); list.add(roleMod); list.add(roleUser);
                roleRepository.saveAll(list);

           List<Location> locations=new ArrayList<Location>();
                locations.add(new Location(1,"Đà Nẵng",36));
                locations.add(new Location(2,"Sài Gòn",75));
                locations.add(new Location(3,"Hà Nội",48));
                locations.add(new Location(4,"Thừa Thiên Huế",20));
                locations.add(new Location(5,"Đà Lạt",34));
                locations.add(new Location(6,"Hội An",45));
                locationRepository.saveAll(locations);

            }
        };

    }*/
}
