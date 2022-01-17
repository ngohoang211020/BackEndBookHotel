package com.bookhotel.service;

import com.bookhotel.entity.Hotel;
import com.bookhotel.entity.Location;
import com.bookhotel.entity.User;
import com.bookhotel.repository.HotelRepository;
import com.bookhotel.repository.LocationRepository;
import com.bookhotel.response.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HotelService {
    public static final int USERS_PER_PAGE = 5;

    @Autowired
    private LocationRepository locationRepo;
    @Autowired
    private HotelRepository hotelRepo;

    public Location findByLocation_Id(Integer id) {
        return locationRepo.findById(id).get();
    }

    public void save(Hotel hotel) {
        hotelRepo.save(hotel);
    }

    public Boolean existsById(Integer id) {
        return hotelRepo.existsById(id);
    }

    public void update(Hotel hotel, Integer id) {
        Hotel existedHotel = hotelRepo.findById(id).get();
        if (existedHotel != null) {
            existedHotel.setAddress(hotel.getAddress());
            existedHotel.setLocation(hotel.getLocation());
            existedHotel.setName(hotel.getName());
            existedHotel.setPhone(hotel.getPhone());
            existedHotel.setRate(hotel.getRate());
            existedHotel.setContent(hotel.getContent());
            existedHotel.setImage(hotel.getImage());
        }
        save(existedHotel);
    }


    public Paging<Hotel> listByPage(Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, USERS_PER_PAGE);
        Page<Hotel> userPage = hotelRepo.findAll(pageable);
        return new Paging<Hotel>(userPage);
    }


    public void deleteById(Integer id) {
        hotelRepo.deleteById(id);
    }


    public List<Hotel> findAll() {
        return hotelRepo.findAll();
    }


    public Hotel findById(Integer id) {
        return hotelRepo.findById(id).orElse(null);
    }

    public List<Hotel> findByKeyword(String keyword){
        return hotelRepo.findByKeyWord(keyword);
    }

}
