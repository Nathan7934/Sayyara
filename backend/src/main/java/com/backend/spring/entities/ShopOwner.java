package com.backend.spring.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class ShopOwner extends AppUser {
    @OneToOne(mappedBy = "shopOwner", fetch = EAGER, optional = false, cascade = ALL)
    private Shop shop;

    public ShopOwner(String firstName, String lastName, String email, String phoneNumber, String username, String password) {
        super(firstName, lastName, email, phoneNumber, username, password);
    }

    public ShopOwner(String firstName, String lastName, String email, String phoneNumber, String username, String password, Shop shop) {
        super(firstName, lastName, email, phoneNumber, username, password);
        this.shop = shop;
    }
}
