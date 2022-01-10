package com.bookhotel.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    @Column(name = "room_id")
    private Integer id;

    private Float price;
    @Column(name = "room_name")
    private String name;
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @OneToMany(fetch =FetchType.LAZY,cascade = CascadeType.ALL
            ,orphanRemoval = true,mappedBy = "room")
    private Set<InformationRoom> informationList;

    @OneToMany(fetch =FetchType.LAZY,cascade = CascadeType.ALL
            ,orphanRemoval = true,mappedBy = "room")
    private Set<RoomService> serviceList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private RoomOrder roomOrder;
}
