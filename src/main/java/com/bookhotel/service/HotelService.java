package com.bookhotel.service;

import com.bookhotel.entity.Hotel;
import com.bookhotel.entity.Location;
import com.bookhotel.repository.HotelRepository;
import com.bookhotel.repository.LocationRepository;
import com.bookhotel.response.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService implements CommonService<Hotel> {
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

    @Override
    public void save(Hotel hotel) {
        hotelRepo.save(hotel);
    }

    @Override
    public void update(Hotel hotel,Integer id) {
        Hotel existedHotel=hotelRepo.findById(id).get();
        if (existedHotel!=null) {
             existedHotel=hotel;
             existedHotel.setId(id);
        }
        save(existedHotel);
    }

    @Override
    public Paging<Hotel> listByPage(Integer pageNumber) {
        return null;
    }


    @Override
    public void deleteById(Integer id) {
        hotelRepo.deleteById(id);
    }

    @Override
    public List<Hotel> findAll() {
        return hotelRepo.findAll();
    }

    @Override
    public Hotel findById(Integer id) {
        return hotelRepo.findById(id).orElse(null);
    }

    @Override
    public Hotel findByName(String name) {
        return null;
    }

    @Override
    public Paging<Hotel> findByKeyWord(String keyword, Integer pageNumber) {
        return null;
    }


}
