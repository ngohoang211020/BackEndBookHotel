package com.bookhotel.repository;

import com.bookhotel.entity.RoomOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomOrderRepository extends JpaRepository<RoomOrder,Integer> {
    List<RoomOrder> findByRoomId(Integer roomId);
}
