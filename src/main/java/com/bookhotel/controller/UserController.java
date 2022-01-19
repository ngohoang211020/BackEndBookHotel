package com.bookhotel.controller;

import com.bookhotel.entity.Comment;
import com.bookhotel.entity.Room;
import com.bookhotel.entity.RoomOrder;
import com.bookhotel.entity.User;
import com.bookhotel.mapper.Mapper;
import com.bookhotel.repository.CommentRepository;
import com.bookhotel.repository.RoomOrderRepository;
import com.bookhotel.repository.RoomRepository;
import com.bookhotel.request.OrderRequest;
import com.bookhotel.request.SignupRequest;
import com.bookhotel.response.ResponseObject;
import com.bookhotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/api/v1/user")
public class UserController {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private RoomOrderRepository roomOrderRepository;

    @Autowired
    private CommentRepository commentRepository;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{userId}/order/false")
    public ResponseEntity<ResponseObject> listRoomOrderFalse(@PathVariable("userId") Integer userId) {
        if (userService.existsById(userId)) {
            List<RoomOrder> temp = roomOrderRepository.findlistOrderByStatus(userId, false);
            List<OrderRequest> orderRequestList = new ArrayList<OrderRequest>();
            List<RoomOrder> roomOrderList = new ArrayList<RoomOrder>();
            temp
                    .stream()
                    .forEach(item -> orderRequestList.add(Mapper.roomOrderToOrderRequest(item)));
            orderRequestList
                    .stream()
                    .forEach(item -> roomOrderList.add(Mapper.orderRequestToRoomOrder(item
                            , roomRepository.findById(item.getRoom_id()).get()
                            , userService.findById(item.getUser_id()))));
            temp.stream().forEach(item -> roomOrderList.get(temp.indexOf(item)).setId(item.getId()));
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Order Room SuccessFull", roomOrderList)
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Fail", "Not Found Exception", "")
            );
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{userId}/order/{orderId}")
    public ResponseEntity<ResponseObject> getOrderByIdForRate(@PathVariable("userId") Integer userId, @PathVariable("orderId") Integer orderId) {
        if (userService.existsById(userId)) {
            OrderRequest orderRequest = Mapper.roomOrderToOrderRequest(roomOrderRepository.findById(orderId).get());
            RoomOrder roomOrder = Mapper.orderRequestToRoomOrder(orderRequest, roomRepository.findById(orderRequest.getRoom_id()).get(), userService.findById(orderRequest.getUser_id()));
            roomOrder.setId(orderId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Order Room SuccessFull", roomOrder)
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Fail", "Not Found Exception", "")
            );
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{userId}/order/true")
    public ResponseEntity<ResponseObject> listRoomOrderTrue(@PathVariable("userId") Integer userId) {
        if (userService.existsById(userId)) {
            List<RoomOrder> temp = roomOrderRepository.findlistOrderByStatus(userId, true);
            List<OrderRequest> orderRequestList = new ArrayList<OrderRequest>();
            List<RoomOrder> roomOrderList = new ArrayList<RoomOrder>();
            temp
                    .stream()
                    .forEach(item -> orderRequestList.add(Mapper.roomOrderToOrderRequest(item)));
            orderRequestList
                    .stream()
                    .forEach(item -> roomOrderList.add(Mapper.orderRequestToRoomOrder(item
                            , roomRepository.findById(item.getRoom_id()).get()
                            , userService.findById(item.getUser_id()))));
            temp.stream().forEach(item -> roomOrderList.get(temp.indexOf(item)).setId(item.getId()));
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Get list order SuccessFull", roomOrderList)
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Fail", "Not Found Exception", "")
            );
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{userId}/order/{roomId}")
    public ResponseEntity<ResponseObject> userOrderRoom(@PathVariable("userId") Integer userId, @PathVariable("roomId") Integer roomId, @RequestBody OrderRequest orderRequest) {

        Room room = roomRepository.findById(orderRequest.getRoom_id()).get();
        User user = userService.findById(orderRequest.getUser_id());
        RoomOrder order = Mapper.orderRequestToRoomOrder(orderRequest
                , room, user);
        roomOrderRepository.save(order);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Order Room SuccessFull", order));

    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{userId}/update")
    public ResponseEntity<ResponseObject> updateUser(@PathVariable("userId") Integer userId, @RequestBody SignupRequest userInfo) {
        if (userService.existsById(userId)) {
            User user = userService.findById(userId);
            user.setName(userInfo.getName());
            user.setPhone(userInfo.getPhone());
            user.setAddress(userInfo.getAddress());
            user.setIdentification(userInfo.getIdentification());
            user.setEmail(userInfo.getEmail());
            user.setPassword(encoder.encode(userInfo.getPassword()));
            userService.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Update User successfully", user)
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Fail", "User Not Found", "")
            );
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{userId}/order/{orderId}/comment")
    public ResponseEntity<ResponseObject> commentOrder(@PathVariable("userId") Integer userId, @PathVariable("orderId") Integer orderId, @RequestBody Comment comment) {
        if (userService.existsById(userId)) {
            RoomOrder order = roomOrderRepository.findById(orderId).get();
            comment.setRoomOrder(order);
            comment.setRoom(roomRepository.findById(order.getRoom().getId()).get());
            commentRepository.save(comment);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Comment Successful", comment)
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Fail", "User Not Found", "")
            );
        }

    }
}