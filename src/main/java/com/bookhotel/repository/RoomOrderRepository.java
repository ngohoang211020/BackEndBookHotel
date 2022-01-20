package com.bookhotel.repository;

import com.bookhotel.entity.RoomOrder;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomOrderRepository extends JpaRepository<RoomOrder,Integer> {
    List<RoomOrder> findByRoomId(Integer roomId);

    @Query("select r from RoomOrder r where r.user.id = ?1 and r.status = ?2 order by r.arrival_date DESC, r.departure_date DESC")
    List<RoomOrder> findlistOrderByStatus(Integer id, boolean status);

    Optional<RoomOrder> findByUser_Id(Integer id);




}
