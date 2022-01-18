package com.bookhotel.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "room_name")
    private String room_name;
    @Column(name = "price")
    private Float price;
    @Column(name = "status",columnDefinition = "boolean default false")
    private Boolean status;
    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false,cascade = CascadeType.ALL)
    @JoinColumn(name = "hotel_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Hotel hotel;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "rooms_services",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
    private Set<RoomService> roomServices = new HashSet<>();

    @Transient
    private String hotel_name;

    @Transient
    private String location_name;

    @OneToMany(mappedBy = "room", cascade = {
            CascadeType.ALL
    })
    private List<RoomOrder> room_orders = new ArrayList<>();

    public Room(Integer id, String room_name, float price, boolean status, String content) {
        this.id = id;
        this.room_name = room_name;
        this.price = price;
        this.status = status;
        this.content = content;
    }


}
