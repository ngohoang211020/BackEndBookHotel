package com.bookhotel.request;

import lombok.Data;

@Data
public class HotelRequest {
    private String name;
    private String address;
    private String phone;
    private String image;
    private String description;
    private Float rate;
    private Integer location_id;
}
