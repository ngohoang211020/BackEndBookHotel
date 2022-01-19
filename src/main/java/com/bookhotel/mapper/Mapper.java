package com.bookhotel.mapper;

import com.bookhotel.entity.Room;
import com.bookhotel.entity.RoomOrder;
import com.bookhotel.request.OrderRequest;
import com.bookhotel.util.StringProccessUtil;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Mapper {

    public static RoomOrder orderRequestToRoomOrder(OrderRequest orderRequest, Room room) {
        RoomOrder order = new RoomOrder();
        order.setName(orderRequest.getName());
        order.setEmail(orderRequest.getEmail());
        order.setPhone(orderRequest.getPhone());
        order.setRoom(room);
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
        Set<String> roomServices=new HashSet<>();
        room.getRoomServices().stream().forEach(item -> roomServices.add(item.getName()));
        order.setRoomService(roomServices);
        return order;
    }
}
