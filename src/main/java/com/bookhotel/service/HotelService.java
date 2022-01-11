package com.bookhotel.service;

import com.bookhotel.entity.Hotel;
import com.bookhotel.entity.Location;
import com.bookhotel.repository.HotelRepository;
import com.bookhotel.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {
    @Autowired
    private LocationRepository locationRepo;
    @Autowired
    private HotelRepository hotelRepo;

    public List<Location> findAllLocation(){
        return locationRepo.findAll();
    }
    public List<Location> findLocationByName(String name){
        return locationRepo.findByName(name);
    }

    public List<Hotel> findByLocation_Id(Integer id){
        return hotelRepo.findByLocation_Id(id);
    }
    public List<Hotel> findByLocation_Name(String name){
        return hotelRepo.findByLocation_Name(name);
    }
}
