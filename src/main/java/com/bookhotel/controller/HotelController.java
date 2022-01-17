package com.bookhotel.controller;


import com.bookhotel.entity.Hotel;
import com.bookhotel.entity.Location;
import com.bookhotel.repository.HotelRepository;
import com.bookhotel.repository.LocationRepository;
import com.bookhotel.response.ResponseObject;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/api/user")
public class HotelController {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Operation(summary = "Lấy danh sách khách sạn tại 1 location", description = "Trả về 1 list hotel", tags = { "Hotel" })
    @GetMapping("/locations/{locationId}/hotels")
    public List<Hotel> getHotelsByLocation(@PathVariable(value = "locationId") Integer locationId) {
        return hotelRepository.findByLocationId(locationId);
    }

    @Operation(summary = "Insert 1 khách sạn tại 1 location", description = "Trả về 1 Response Object", tags = { "Hotel" })
    @PostMapping("/locations/{locationId}/hotels")
    ResponseEntity<ResponseObject> createHotel(@PathVariable(value = "locationId") Integer locationId, @RequestBody Hotel newHotel) {
        Optional<Location> foundLocationId = locationRepository.findById(locationId);
        if (foundLocationId.isPresent()) {
            foundLocationId.map(location -> {
                newHotel.setLocation(location);
                return hotelRepository.save(newHotel);
            });
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query location successfully", foundLocationId)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find location", "")
            );
        }
    }

    @Operation(summary = "Update 1 khach san theo id", description = "Trả về 1 Response Object", tags = { "Hotel" })
    @PutMapping("/locations/{locationId}/hotels/{hotelId}")
    ResponseEntity<ResponseObject> updateHotel(@PathVariable(value = "locationId") Integer locationId,
                                               @PathVariable(value = "hotelId") Integer hotelId, @RequestBody Hotel hotelRequest) {
        if (!locationRepository.existsById(locationId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find location", "")
            );
        } else {
            Hotel updateHotel = hotelRepository.findById(hotelId).map(hotel -> {
                hotel.setAddress(hotelRequest.getAddress());
                hotel.setName(hotelRequest.getName());
                hotel.setPhone(hotelRequest.getPhone());
                hotel.setContent(hotelRequest.getContent());
                hotel.setRate(hotelRequest.getRate());
                hotel.setImage(hotelRequest.getImage());
                return hotelRepository.save(hotel);
            }).orElseGet(() -> {
                hotelRequest.setId(hotelId);
                return hotelRepository.save(hotelRequest);
            });
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Update Hotel Successfully", updateHotel)
            );
        }
    }

    @Operation(summary = "Delete 1 hotel theo id", description = "Trả về ResponseObject", tags = { "Hotel" })
    @DeleteMapping("/locations/{locationId}/hotels/{hotelId}")
    ResponseEntity<ResponseObject> deleteHotel(@PathVariable(value = "locationId") Integer locationId,
                                               @PathVariable(value = "hotelId") Integer hotelId) {
        if (locationRepository.findById(locationId).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find location", "")
            );
        } else {
            boolean exists = hotelRepository.existsById(hotelId);
            if (exists) {
                hotelRepository.deleteById(hotelId);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Delete Hotel Successfully", "")
                );
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot find hotel to delete", "")
                );
            }
        }
    }
}