package com.bookhotel.controller;


import com.bookhotel.entity.Location;
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
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;
    @Operation(summary = "Lấy danh sách location ", description = "Trả về danh sách location", tags = { "Location" })
    @GetMapping("/locations")
    public List<Location> getLocations() {
        return locationRepository.findAll();
    }

    @Operation(summary = "Lấy Location theo id", description = "Trả về 1 location", tags = { "Location" })
    @GetMapping("/locations/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Integer id) {
        Optional<Location> foundLocation = locationRepository.findById(id);
        return foundLocation.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query location successfully", foundLocation)
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot find location with id = " + id, "")
                );
    }

    @Operation(summary = "Tim kiem Location theo keyword", description = "Trả về 1 listLocation", tags = { "Location" })
    @GetMapping("/locations/keyword")
    ResponseEntity<ResponseObject> findByKeyWord(@RequestParam(value = "location") String keyword) {
//        Optional<Location> foundLocation = locationRepository.findById(id);
        return keyword == null ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query location successfully", locationRepository.findAll())
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("ok", "Query location successfully", locationRepository.getByKeyword(keyword))
                );
    }

    @Operation(summary = "Insert 1 location", description = "Trả về messageResponse", tags = { "Location" })
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
    @Operation(summary = "Update 1 location", description = "Trả về messageResponse", tags = { "Location" })
    @PutMapping("/locations/{id}")
    ResponseEntity<ResponseObject> updateLocation(@RequestBody Location newLocation, @PathVariable Integer id) {
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

    @Operation(summary = "Delete 1 location", description = "Trả về messageResponse", tags = { "Location" })
    @DeleteMapping("/locations/{id}")
    ResponseEntity<ResponseObject> deleteLocation(@PathVariable Integer id) {
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
}
