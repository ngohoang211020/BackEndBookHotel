package com.bookhotel.repository;

import com.bookhotel.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
    @Query("select l from Location l where l.location LIKE %?1%")
    List<Location> getByKeyword(String keyword);

    List<Location> findByLocation(String trim);

    boolean existsByLocation(String location);


}
