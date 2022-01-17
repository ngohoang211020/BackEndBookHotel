package com.bookhotel.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "location")
public class Location extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "location", nullable = false,  length = 300)
    private String location;

    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy = "location", cascade = {CascadeType.ALL})
    private List<Hotel> hotels = new ArrayList<>();

    @Transient
    private int number_hotels;

    public int getNumber_hotels() {
        this.number_hotels = 0;
        if (!hotels.isEmpty()) {
            this.number_hotels = hotels.size();
        }
        return number_hotels;
    }

    public Location(Integer location_id, String location, String image) {
        this.id = location_id;
        this.location = location;
        this.image = image;
    }


}