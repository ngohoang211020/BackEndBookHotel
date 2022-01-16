package com.bookhotel.controller;

import com.bookhotel.entity.Role;
import com.bookhotel.response.MessageResponse;
import com.bookhotel.response.ResponseObject;
import com.bookhotel.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("api/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Operation(summary = "Lấy danh sách Role ", description = "Trả về danh sách Role", tags = {"Role"})
    @GetMapping()
    public List<Role> findAll() {
        return roleService.findAll();
    }

    @Operation(summary = "Lấy Role 1 theo id ", description = "Trả về 1 Role", tags = {"Role"})
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Integer id) {
        Optional<Role> foundRole = Optional.ofNullable(roleService.findById(id));
        return foundRole.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query Role successfully", foundRole)
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot find Role with id = " + id, "")
                );
    }

    @Operation(summary = "Insert Role ", description = "Trả về message thông báo kết quả", tags = {"Role"})
    @PostMapping
    public ResponseEntity<MessageResponse> addRole(@RequestBody Role role) {
        if (roleService.existsById(role.getId())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Role is already taken!", false));

        }
        if (roleService.existsByName(role.getName().name())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Role is already taken!", false));

        } else {
            roleService.save(role);
        }
        return ResponseEntity.ok(new MessageResponse("Role inserted successfully!", true));
    }

    @Operation(summary = "Update 1 Role theo id", description = "Trả về 1 message thông báo", tags = {"Role"})
    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateRole(@RequestBody Role role, @PathVariable("id") Integer id) {
        roleService.update(role, id);
        return ResponseEntity.ok(new MessageResponse("User updated Succesfully!!!", true));
    }

    @Operation(summary = "Delete 1 Role theo id", description = "Trả về 1 message thông báo", tags = {"Role"})
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteRole(@PathVariable int id) {
        Boolean exists = roleService.existsById(id);
        if (exists) {
            roleService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete Role Successfully", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find Role to delete", "")
            );
        }
    }
}
