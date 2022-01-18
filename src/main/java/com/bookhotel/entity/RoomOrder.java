package com.bookhotel.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Column(name = "payment")
    private Boolean payment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    @Transient
    private String room_name;

    @Transient
    private String hotel_name;

    @Transient
    private String location_name;

    @Transient
    private Float roomCharge;



}
