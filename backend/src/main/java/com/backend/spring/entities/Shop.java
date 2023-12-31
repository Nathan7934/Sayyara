package com.backend.spring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shops")
public class Shop {
    @Id
    @SequenceGenerator(name = "shop_sequence", sequenceName = "shop_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "shop_sequence")
    @Column(name = "shop_id")
    private Long id;

    @Column(name = "shop_name", nullable = false, unique = true)
    private String name;

    @ToString.Exclude
    @JsonIgnore
    @OneToOne(cascade = ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private ShopOwner shopOwner;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id", nullable = false)
    private Address address;

    @OneToMany(mappedBy = "shop", fetch = FetchType.EAGER)
    private List<Appointment> appointments = new ArrayList<>();

    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Quote> quotes = new ArrayList<>();

    @OneToMany(mappedBy = "shop", fetch = FetchType.EAGER, cascade = ALL)
    private List<Service> services = new ArrayList<>();

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;
    @Column(name = "email", unique = true)
    private String email;

    public Shop(String name, Address address, String phoneNumber, String email, List<Service> services) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.services = services;
    }

    public void update(Shop newShop) {
        this.name = newShop.name;
        this.address.update(newShop.address);
        this.phoneNumber = newShop.phoneNumber;
        this.email = newShop.email;
    }

}
