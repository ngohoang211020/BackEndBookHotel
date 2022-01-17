package com.bookhotel.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Table(name = "rooms")
public class Room extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "room_name")
    private String room_name;
    @Column(name = "price")
    private float price;
    @Column(name = "status")
    private boolean status;
    @Column(name = "content")
    private String content;

    @Column(name = "service")
    private String service = null;

    //    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "hotel_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hotel_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Hotel hotel;

    @Transient
    private String hotel_name;

    @Transient
    private String location_name;

    @OneToMany(mappedBy = "room", cascade = {
            CascadeType.ALL
    })
    private List<RoomOrder> room_orders = new ArrayList<>();


    //    public void setRoom_orders(List<Room> room_orders) {
//        this.room_orders = room_orders;
//    }

    //    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinTable(name = "room_services",
//            joinColumns = {
//                    @JoinColumn(name = "room_id")
//            },
//            inverseJoinColumns = {
//                    @JoinColumn(name = "service_id")
//            })
//    private Set< Service > services = new HashSet< >();
//
//    public Set<Service> getServices() {
//        return services;
//    }
//
//    public void setServices(Set<Service> services) {
//        this.services = services;
//    }


    public Room(Integer id, String room_name, float price, boolean status, String content) {
        this.id = id;
        this.room_name = room_name;
        this.price = price;
        this.status = status;
        this.content = content;
    }


}
