package com.bookhotel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Integer id;
    @Column(name = "location_name")
    private String name;
    @Column(name = "number_hotels")
    private Integer numberHotels;
    @JsonIgnore
    @OneToMany(fetch =FetchType.LAZY,cascade = CascadeType.ALL
            ,mappedBy = "location")
    private Set<Hotel> hotelList;

    public Location(Integer id, String name,Integer numberHotels) {
        this.id = id;
        this.name = name;
        this.numberHotels=numberHotels;
    }
}
