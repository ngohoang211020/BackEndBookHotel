package com.bookhotel.controller;


import com.bookhotel.entity.Hotel;
import com.bookhotel.entity.Room;
import com.bookhotel.entity.RoomOrder;
import com.bookhotel.repository.HotelRepository;
import com.bookhotel.repository.LocationRepository;
import com.bookhotel.repository.RoomOrderRepository;
import com.bookhotel.repository.RoomRepository;
import com.bookhotel.request.OrderRequest;
import com.bookhotel.response.ResponseObject;
import com.bookhotel.util.StringProccessUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    @Autowired
    private RoomOrderRepository roomOrderRepository;

    @Autowired
    private LocationRepository locationRepository;

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
                room.setStatus(roomRequest.getStatus());
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

    @PostMapping("/hotels/{hotelId}/rooms/{roomId}/order")
    public ResponseEntity<ResponseObject> orderRoom(@RequestBody OrderRequest orderRequest, @PathVariable("hotelId") Integer hotelId, @PathVariable("roomId") Integer roomId) {
        RoomOrder order = new RoomOrder();
        order.setName(orderRequest.getName());
        order.setEmail(orderRequest.getEmail());
        order.setPhone(orderRequest.getPhone());
        order.setIdentity_card(orderRequest.getIdentification());
        Date arrivalDate= StringProccessUtil.StringToDate(orderRequest.getArrival_date());
        Date departureDate=StringProccessUtil.StringToDate(orderRequest.getDeparture_date());
        order.setArrival_date(arrivalDate);
        order.setDeparture_date(departureDate);
        order.setName(orderRequest.getName());
        Hotel hotel = hotelRepository.findById(hotelId).get();
        Room room = roomRepository.findById(roomId).get();
        room.setService(orderRequest.getService());
        order.setRoom(room);
        order.setHotel_name(hotel.getName());
        order.setRoom_name(room.getRoom_name());
        order.setLocation_name(hotel.getLocation().getLocation());
        order.setRoomCharge(room.getPrice() * StringProccessUtil.daysBetween2Dates(arrivalDate, departureDate));
        roomOrderRepository.save(order);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Order Room SuccessFull", order)
        );
    }

}
