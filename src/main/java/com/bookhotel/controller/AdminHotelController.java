package com.bookhotel.controller;

import com.bookhotel.entity.Hotel;
import com.bookhotel.entity.Location;
import com.bookhotel.repository.HotelRepository;
import com.bookhotel.repository.LocationRepository;
import com.bookhotel.repository.UserRepository;
import com.bookhotel.request.HotelRequest;
import com.bookhotel.response.MessageResponse;
import com.bookhotel.response.ResponseObject;
import com.bookhotel.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminHotelController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "Lấy tat ca Location ", description = "Trả về 1 list location", tags = { "Admin/Hotel" })
    @GetMapping("locations")
    public List<Location> getLocations() {
        return locationRepository.findAll();
    }

    @Operation(summary = "Lấy Location theo id", description = "Trả về 1 location", tags = { "Admin/Hotel" })
    @GetMapping("/locations/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable int id) {
        Optional<Location> foundLocation = locationRepository.findById(id);
        return foundLocation.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query location successfully", foundLocation)
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot find location with id = " + id, "")
                );
    }

    @Operation(summary = "Thêm 1 location", description = "Trả về 1 location", tags = { "Admin/Hotel" })
    @PostMapping("/locations/insert")
    ResponseEntity<ResponseObject> insertLocation(@RequestBody Location newLocation) {
        List<Location> foundLocations = locationRepository.findByLocation(newLocation.getLocation().trim());
        if (foundLocations.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Location name already taken", "")
            );

        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert Location Successfully", locationRepository.save(newLocation))
        );
    }

    @Operation(summary = "Update 1 location", description = "Trả về 1 location", tags = { "Admin/Hotel" })
    @PutMapping("/locations/{id}")
    ResponseEntity<ResponseObject> updateLocation(@RequestBody Location newLocation, @PathVariable int id) {
        Location updatedLocation = locationRepository.findById(id).map(location -> {
            location.setLocation(newLocation.getLocation());
            location.setImage(newLocation.getImage());
            return locationRepository.save(location);
        }).orElseGet(() -> {
            newLocation.setId(id);
            return locationRepository.save(newLocation);
        });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update Location Successfully", updatedLocation)
        );
    }

    @Operation(summary = "Delete 1 location", description = "Tra ve message", tags = { "Admin/Hotel" })
    @DeleteMapping("/locations/{id}")
    ResponseEntity<ResponseObject> deleteLocation(@PathVariable int id) {
        boolean exists = locationRepository.existsById(id);
        if (exists) {
            locationRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete Location Successfully", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find location to delete", "")
            );
        }
    }

    @Operation(summary = "Lấy danh sách khách sạn ở trang đầu tiên", description = "Trả về danh sách khách sạn ở trang 1", tags = {"Admin/Hotel"})
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/hotels")
    public ResponseEntity<ResponseObject> listFirstPage() {
        return listByPage(1);
    }

    @Operation(summary = "Lấy danh sách khách sạn ở trang n", description = "Trả về danh sách khách sạn ở trang n", tags = {"Admin/Hotel"})
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/hotels/{pageNum}")
    public ResponseEntity<ResponseObject> listByPage(@PathVariable("pageNum") Integer pageNum) {

        return !hotelService.listByPage(pageNum).getContent().isEmpty() ? ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Query Hotels by page successfully", hotelService.listByPage(1))) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Cannot Find Hotels", ""));
    }

    @Operation(summary = "Tìm kiếm khách sạn với từ khoá", description = "Trả về danh sách khách sạn cần tìm", tags = {"Admin/Hotel"})
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/hotels")
    public ResponseEntity<ResponseObject> searchUser(@RequestParam("keyword") String keyword) {
        return hotelService.findByKeyword(keyword).size() > 0 ? ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Query Hotels by keyword successfully", hotelService.findByKeyword(keyword))) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Cannot Find Hotels", ""));
    }

    @Operation(summary = "Tạo mới 1 khách sạn", description = "Trả về 1 message thông báo", tags = {"Admin/Hotel"})
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/hotels/save")
    public ResponseEntity<MessageResponse> insertHotel(@RequestBody HotelRequest hotelRequest) {
        Location location = hotelService.findByLocation_Id(hotelRequest.getLocation_id());

        Hotel hotel = new Hotel(hotelRequest.getName(), hotelRequest.getAddress(), hotelRequest.getPhone(), hotelRequest.getRate(), hotelRequest.getDescription(), hotelRequest.getImage(), location);
        hotelService.save(hotel);

        return ResponseEntity.ok(new MessageResponse("Create hotel successfully!", true));
    }

    @Operation(summary = "Update 1 khách sạn theo id", description = "Trả về 1 message thông báo", tags = {"Admin/Hotel"})
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/hotels/{id}")
    public ResponseEntity<MessageResponse> updateUser(@RequestBody HotelRequest hotelRequest, @PathVariable("id") Integer id) {
        Location location = hotelService.findByLocation_Id(hotelRequest.getLocation_id());
        Hotel hotel = new Hotel(hotelRequest.getName(), hotelRequest.getAddress(), hotelRequest.getPhone(), hotelRequest.getRate(), hotelRequest.getDescription(), hotelRequest.getImage(), location);
        hotelService.update(hotel, id);
        return ResponseEntity.ok(new MessageResponse("Hotel updated Succesfully!!!", true));
    }

    @Operation(summary = "Xoá 1 khách sạn theo id", description = "Trả về 1 message thông báo", tags = {"Admin/Hotel"})
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
