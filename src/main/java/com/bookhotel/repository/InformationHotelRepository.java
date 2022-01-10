package com.bookhotel.repository;

import com.bookhotel.entity.InformationHotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InformationHotelRepository extends JpaRepository<InformationHotel, Integer> {
}
