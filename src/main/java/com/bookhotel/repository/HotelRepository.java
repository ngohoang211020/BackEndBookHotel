package com.bookhotel.repository;

import com.bookhotel.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    @Query("select h from Hotel h where h.location.id = ?1")
    List<Hotel> findByLocation_Id(Integer id);

    @Query("SELECT h FROM Hotel h inner join Location l on h.location.id=l.id where l.name like %?1%")
    List<Hotel> findByLocation_Name(String name);

}
