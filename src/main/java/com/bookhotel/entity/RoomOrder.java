package com.bookhotel.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomOrder extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "identity_card")
    private String identity_card;

    @Column(name = "arrival_date")
    private Date arrival_date;

    @Column(name = "departure_date")
    private Date departure_date;

    @Column(name = "number_of_people")
    private int number_of_people;

    @Column(name = "payment")
    private boolean payment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Room room;

    @Transient
    private String room_name;

    @Transient
    private String hotel_name;

    @Transient
    private String location_name;


    public RoomOrder(Integer id, String name, String phone, String email, String identity_card, Date arrival_date, Date departure_date, int number_of_people, boolean payment) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.identity_card = identity_card;
        this.arrival_date = arrival_date;
        this.departure_date = departure_date;
        this.number_of_people = number_of_people;
        this.payment = payment;
    }

}
