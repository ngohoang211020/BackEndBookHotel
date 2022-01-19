package com.bookhotel.mapper;

import com.bookhotel.entity.Room;
import com.bookhotel.entity.RoomOrder;
import com.bookhotel.entity.User;
import com.bookhotel.request.OrderRequest;
import com.bookhotel.util.StringProccessUtil;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Mapper {
    public static RoomOrder orderRequestToRoomOrder(OrderRequest orderRequest, Room room, User user) {
        RoomOrder order = new RoomOrder();
        order.setName(orderRequest.getName());
        order.setEmail(orderRequest.getEmail());
        order.setPhone(orderRequest.getPhone());
        order.setRoom(room);
        order.setStatus(orderRequest.isStatus());
        order.setIdentity_card(orderRequest.getIdentification());
        Date arrivalDate = StringProccessUtil.StringToDate(orderRequest.getArrival_date());
        Date departureDate = StringProccessUtil.StringToDate(orderRequest.getDeparture_date());
        order.setArrival_date(arrivalDate);
        order.setDeparture_date(departureDate);
        order.setName(orderRequest.getName());
        order.setNumber_of_people(orderRequest.getNumber_of_people());
        order.setRoomCharge(room.getPrice() * StringProccessUtil.daysBetween2Dates(arrivalDate, departureDate));
        order.setHotel_name(room.getHotel().getName());
        order.setRoom_name(room.getRoom_name());
        order.setLocation_name(room.getHotel().getLocation().getLocation());
        Set<String> roomServices = new HashSet<>();
        room.getRoomServices().stream().forEach(item -> roomServices.add(item.getName()));
        order.setRoomService(roomServices);
        order.setUser(user);
        return order;
    }
    public static OrderRequest roomOrderToOrderRequest(RoomOrder roomOrder) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setName(roomOrder.getName());
        orderRequest.setEmail(roomOrder.getEmail());
        orderRequest.setStatus(roomOrder.isStatus());
        orderRequest.setPhone(roomOrder.getPhone());
        orderRequest.setArrival_date(StringProccessUtil.DateToString(roomOrder.getArrival_date()));
        orderRequest.setDeparture_date(StringProccessUtil.DateToString(roomOrder.getDeparture_date()));
        orderRequest.setIdentification(orderRequest.getIdentification());
        orderRequest.setNumber_of_people(orderRequest.getNumber_of_people());
        orderRequest.setUser_id(roomOrder.getUser().getId());
        orderRequest.setRoom_id(roomOrder.getRoom().getId());
        return orderRequest;
    }
}
