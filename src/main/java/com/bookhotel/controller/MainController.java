package com.bookhotel.controller;

import com.bookhotel.entity.Hotel;
import com.bookhotel.entity.Location;
import com.bookhotel.response.ResponseObject;
import com.bookhotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//user khi chua dang nhap dung controller nay
@RestController
@RequestMapping("/api/homepage")
public class MainController {
    @Autowired
    HotelService hotelService;

    @GetMapping
    List<Location> getAllLocations() {
        return hotelService.findAllLocation();
    }

    @PostMapping
    public ResponseEntity<ResponseObject> searchLocation(@RequestParam("location") String location) {
        List<Location> locationList = hotelService.findLocationByName(location);
        if (locationList.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", "Search Successful", locationList));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Fail", "Location not found", null));
        }
    }
    @GetMapping("/{location}")
    public ResponseEntity<ResponseObject> hotelInLocation(@PathVariable("location") String location) {
        List<Hotel> hotelList = hotelService.findByLocation_Name(location);
        if (hotelList.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", "Search Successful", hotelList));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Fail", "Hotel not found", null));
        }
    }
}
