package com.bookhotel.service;

import com.bookhotel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private RoomServiceRepository roomServiceRepository;
    @Autowired
    private RoomOrderRepository roomOrderRepository;
    @Autowired
    private InformationRoomRepository informationRoomRepository;


}
