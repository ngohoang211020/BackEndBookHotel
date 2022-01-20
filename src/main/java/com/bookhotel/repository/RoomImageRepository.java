package com.bookhotel.repository;


import com.bookhotel.entity.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomImageRepository extends JpaRepository<RoomImage, Integer> {
    List<RoomImage> findByRoomId(int roomId);
}
