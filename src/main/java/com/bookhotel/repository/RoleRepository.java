package com.bookhotel.repository;


import com.bookhotel.entity.Role;
import com.bookhotel.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);

    Boolean existsByName(String name);
}