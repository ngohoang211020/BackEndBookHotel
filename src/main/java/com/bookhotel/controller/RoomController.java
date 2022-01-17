package com.bookhotel.controller;


import com.bookhotel.entity.Hotel;
import com.bookhotel.entity.Room;
import com.bookhotel.repository.HotelRepository;
import com.bookhotel.repository.RoomRepository;
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
public class RoomController {


    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Operation(summary = "Get room theo hotel", description = "Trả về list Room", tags = { "Room" })
    @GetMapping("/hotels/{hotelId}/rooms")
    public List<Room> getRoomsByHotel(@PathVariable(value = "hotelId") Integer hotelId) {
        return roomRepository.findByHotelId(hotelId);
    }

    @Operation(summary = "Insert room theo hotel", description = "Trả về ResponseObject", tags = { "Room" })
    @PostMapping("/hotels/{hotelId}/rooms")
    ResponseEntity<ResponseObject> createRoom(@PathVariable(value = "hotelId") Integer hotelId, @RequestBody Room newRoom){
        Optional<Hotel> foundHotelId = hotelRepository.findById(hotelId);
        if(foundHotelId.isPresent()){
            foundHotelId.map(location -> {
                newRoom.setHotel(location);
                return roomRepository.save(newRoom);
            });
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query room successfully",foundHotelId)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed","Cannot find hotel", "")
            );
        }
    }
    @Operation(summary = "Update room theo hotel", description = "Trả về ResponseObject", tags = { "Room" })
    @PutMapping("/hotels/{hotelId}/rooms/{roomId}")
    ResponseEntity<ResponseObject> updateRoom(@PathVariable(value = "hotelId") Integer hotelId,
                                               @PathVariable(value = "roomId") Integer roomId,@RequestBody Room roomRequest) {
        if (!hotelRepository.existsById(hotelId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed","Cannot find hotel", "")
            );
        }else{
            Room updateRoom = roomRepository.findById(roomId).map(room -> {
                room.setRoom_name(roomRequest.getRoom_name());
                room.setPrice(roomRequest.getPrice());
                room.setStatus(roomRequest.isStatus());
                room.setContent(roomRequest.getContent());
                room.setService(roomRequest.getService());
                return roomRepository.save(room);
            }).orElseGet(()->{
                roomRequest.setId(roomId);
                return roomRepository.save(roomRequest);
            });
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok","Update Room Successfully", updateRoom)
            );
        }
    }

    @Operation(summary = "Delete room theo hotel", description = "Trả về ResponseObject", tags = { "Room" })
    @DeleteMapping("/hotels/{hotelId}/rooms/{roomId}")
    ResponseEntity<ResponseObject> deleterRoom(@PathVariable(value = "hotelId") Integer hotelId,
                                               @PathVariable(value = "roomId") Integer roomId){
        if (!hotelRepository.findById(hotelId).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed","Cannot find location", "")
            );
        }else{
            boolean exists = roomRepository.existsById(roomId);
            if(exists){
                roomRepository.deleteById(roomId);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok","Delete Room Successfully", "")
                );
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed","Cannot find room to delete", "")
                );
            }
        }
    }
}
