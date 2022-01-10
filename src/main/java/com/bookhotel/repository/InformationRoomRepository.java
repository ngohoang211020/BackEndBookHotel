package com.bookhotel.repository;

import com.bookhotel.entity.InformationRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InformationRoomRepository extends JpaRepository<InformationRoom, Integer> {
}
