package com.bookhotel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RoomOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "identity_card")
    private String identity_card;

    @Column(name = "arrival_date")
    @Temporal(TemporalType.DATE)
    private Date arrival_date;

    @Column(name = "departure_date")
    @Temporal(TemporalType.DATE)
    private Date departure_date;

    @Column(name = "number_of_people")
    private Integer number_of_people;

    @Column(name = "payment",columnDefinition = "boolean default false")
    private Boolean payment;

    private Float roomCharge;

    @Column(columnDefinition = "boolean default false")
    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    @JsonIgnore
    private Room room;


    @ManyToOne(fetch = FetchType.LAZY,optional = true)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;


    @Transient
    private String room_name;

    @Transient
    private String hotel_name;

    @Transient
    private String location_name;

    @Transient
    private Set<String> roomService=new HashSet<>();

}
