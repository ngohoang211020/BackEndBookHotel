package com.bookhotel.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InformationHotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inforHotel_id")
    private Integer id;

    private String content;
    private String image;
    @OneToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
}
