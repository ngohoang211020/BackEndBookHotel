package com.bookhotel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotel_id")
    private Integer id;
    @Column(name = "hotel_name")
    private String name;

    private String address;

    private String phone;

    private Float rate;

    private String content;

    private String image;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @JsonIgnore
    @OneToMany(fetch =FetchType.LAZY,cascade = CascadeType.ALL
            ,orphanRemoval = true,mappedBy = "hotel")
    private List<Room> roomList;

    @JsonIgnore
    @OneToMany(fetch =FetchType.LAZY,cascade = CascadeType.ALL
            ,orphanRemoval = true,mappedBy = "hotel")
    private Set<RoomService> roomServices;

    public Hotel(String name, String address, String phone, Float rate, String content, String image, Location location) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.rate = rate;
        this.content = content;
        this.image = image;
        this.location = location;
    }
}
