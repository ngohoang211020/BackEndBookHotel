package com.bookhotel.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer id;
    @Column(name = "arrival_date")
    private Date arrivalDate;
    @Column(name = "departure_date")
    private Date departureDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch =FetchType.LAZY,cascade = CascadeType.ALL
            ,orphanRemoval = true,mappedBy = "roomOrder")
    private Set<Room> roomList;
}
