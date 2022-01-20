package com.bookhotel.controller;

import com.bookhotel.entity.Hotel;
import com.bookhotel.entity.Location;
import com.bookhotel.entity.User;
import com.bookhotel.enums.ERole;
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

import java.util.ArrayList;
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
    public ResponseEntity<ResponseObject> findById(@PathVariable int id){
        Optional<Location> foundLocation = locationRepository.findById(id);
        return foundLocation.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query location successfully",foundLocation)
                ):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot find location with id = " + id,"")
                );
    }


    @Operation(summary = "Thêm 1 location", description = "Trả về 1 location", tags = { "Admin/Hotel" })
    @PostMapping("/locations/insert")
    public ResponseEntity<ResponseObject> insertLocation(@RequestBody Location newLocation){
        List<Location> foundLocations = locationRepository.findByLocation(newLocation.getLocation().trim());
        if(foundLocations.size() > 0){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed","Location name already taken","")
            );

        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok","Insert Location Successfully",locationRepository.save(newLocation))
        );
    }

    @Operation(summary = "Update 1 location", description = "Trả về 1 location", tags = { "Admin/Hotel" })
    @PutMapping("/locations/{id}")
    public ResponseEntity<ResponseObject> updateLocation(@RequestBody Location newLocation, @PathVariable int id){
        Location updatedLocation = locationRepository.findById(id).map(location -> {
            location.setLocation(newLocation.getLocation());
            location.setImage(newLocation.getImage());
            return locationRepository.save(location);
        }).orElseGet(()->{
            newLocation.setId(id);
            return locationRepository.save(newLocation);
        });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok","Update Location Successfully", updatedLocation)
        );
    }

    @Operation(summary = "Delete 1 location", description = "Tra ve message", tags = { "Admin/Hotel" })
    @DeleteMapping("/locations/{id}")
    public ResponseEntity<ResponseObject> deleteLocation(@PathVariable int id){
        boolean exists = locationRepository.existsById(id);
        if(exists){
            locationRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok","Delete Location Successfully", "")
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed","Cannot find location to delete", "")
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

        return !hotelService.listByPage(pageNum).getContent().isEmpty() ? ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Query Hotels by page successfully", hotelService.listByPage(pageNum))) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "No Hotel in page "+ pageNum, ""));
    }

    @Operation(summary = "Tìm kiếm khách sạn với từ khoá", description = "Trả về danh sách khách sạn cần tìm", tags = {"Admin/Hotel"})
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/hotels")
    public ResponseEntity<ResponseObject> searchUser(@RequestParam("keyword") String keyword) {
        return hotelService.findByKeyword(keyword).size()>0 ? ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Query Hotels by keyword successfully", hotelService.findByKeyword(keyword))) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Cannot Find Hotels", ""));
    }

    @Operation(summary = "Tạo mới 1 khách sạn", description = "Trả về 1 message thông báo", tags = {"Admin/Hotel"})
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/hotels/save")
    public ResponseEntity<ResponseObject> createHotel(@PathVariable(value = "locationId") int locationId,@PathVariable(value = "userId") int userId, @RequestBody Hotel newHotel){
        User foundUserId = userRepository.findById(userId).get();
        Optional<Location> foundLocationId = locationRepository.findById(locationId);
        if(foundLocationId.isPresent()){
            newHotel.setUser(foundUserId);
            foundLocationId.map(location -> {
                newHotel.setLocation(location);
                return hotelRepository.save(newHotel);
            });
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query location successfully",foundLocationId)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed","Cannot find location", "")
            );
        }
    }

    @Operation(summary = "Update 1 khách sạn theo id", description = "Trả về 1 message thông báo", tags = {"Admin/Hotel"})
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/hotels/{id}")
    public ResponseEntity<MessageResponse> updateHotel(@RequestBody HotelRequest hotelRequest, @PathVariable("id") Integer id) {
        Location location = hotelService.findByLocation_Id(hotelRequest.getLocation_id());
        Hotel hotel = new Hotel(hotelRequest.getName(), hotelRequest.getAddress(), hotelRequest.getPhone(), hotelRequest.getRate(), hotelRequest.getDescription(), hotelRequest.getImage(), location);
        hotelService.update(hotel, id);
        return ResponseEntity.ok(new MessageResponse("Hotel updated Succesfully!!!", true));
    }

    @Operation(summary = "Xoá 1 khách sạn theo id", description = "Trả về 1 message thông báo", tags = {"Admin/Hotel"})
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/hotels/{hotelId}")
    ResponseEntity<ResponseObject> deleteHotel(@PathVariable(value = "hotelId") int hotelId){

        boolean exists = hotelRepository.existsById(hotelId);
        if(exists){
            hotelRepository.deleteById(hotelId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok","Delete Hotel Successfully", "")
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed","Cannot find hotel to delete", "")
            );
        }

    }
    @Operation(summary = "Lấy danh sách các manager chưa quản lý khách sạn nào", description = "Trả về danh sách các manager", tags = { "Admin/Hotel" })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/managers")
    public List<User> getUserNotManageHotel() {
        List<User> userList = new ArrayList<>();
        List<User> usersManager = userRepository.findByRolesName(ERole.ROLE_MODERATOR);
        usersManager.forEach(user -> {
            if(hotelRepository.findByUserId(user.getId()).size() == 0){
                User user1 = new User();
                user1.setId(user.getId());
                user1.setName(user.getName());
                userList.add(user1);
            }
        });
        return userList;
    }

}
