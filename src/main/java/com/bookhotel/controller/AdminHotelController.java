package com.bookhotel.controller;

import com.bookhotel.entity.Hotel;
import com.bookhotel.entity.Location;
import com.bookhotel.request.HotelRequest;
import com.bookhotel.response.MessageResponse;
import com.bookhotel.response.ResponseObject;
import com.bookhotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin")
public class AdminHotelController {

    @Autowired
    private HotelService hotelService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/hotels")
    public ResponseEntity<ResponseObject> listFirstPage() {
        return listByPage(1);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/hotels/{pageNum}")
    public ResponseEntity<ResponseObject> listByPage(@PathVariable("pageNum") Integer pageNum) {

        return !hotelService.listByPage(pageNum).getContent().isEmpty() ? ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Query Hotels by page successfully", hotelService.listByPage(1))) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Cannot Find Hotels", ""));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/hotels")
    public ResponseEntity<ResponseObject> searchUser(@RequestParam("keyword") String keyword) {
        return hotelService.findByKeyword(keyword).size()>0 ? ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Query Hotels by keyword successfully", hotelService.findByKeyword(keyword))) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Cannot Find Hotels", ""));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/hotels/save")
    public ResponseEntity<MessageResponse> insertHotel(@RequestBody HotelRequest hotelRequest) {
        Location location = hotelService.findByLocation_Id(hotelRequest.getLocation_id());

        Hotel hotel = new Hotel(hotelRequest.getName(), hotelRequest.getAddress(), hotelRequest.getPhone(), hotelRequest.getRate(), hotelRequest.getDescription(), hotelRequest.getImage(), location);
        hotelService.save(hotel);

        return ResponseEntity.ok(new MessageResponse("Create hotel successfully!", true));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/hotels/{id}")
    public ResponseEntity<MessageResponse> updateUser(@RequestBody HotelRequest hotelRequest, @PathVariable("id") Integer id) {
        Location location = hotelService.findByLocation_Id(hotelRequest.getLocation_id());
        Hotel hotel = new Hotel(hotelRequest.getName(), hotelRequest.getAddress(), hotelRequest.getPhone(), hotelRequest.getRate(), hotelRequest.getDescription(), hotelRequest.getImage(), location);
        hotelService.update(hotel, id);
        return ResponseEntity.ok(new MessageResponse("Hotel updated Succesfully!!!", true));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/hotels/{id}")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable("id") Integer id) {
        Boolean exists = hotelService.existsById(id);
        if (exists) {
            hotelService.deleteById(id);
            return ResponseEntity.ok(new MessageResponse("Hotel deleted Succesfully!!!", true));

        } else {
            return ResponseEntity.ok(new MessageResponse("Deleted Hotel fail!!!", false));
        }
    }

}