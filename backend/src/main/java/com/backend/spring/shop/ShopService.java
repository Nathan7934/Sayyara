package com.backend.spring.shop;

import com.backend.spring.user.security.AuthHeaderParser;
import com.backend.spring.user.shopowner.ShopOwner;
import com.backend.spring.user.shopowner.ShopOwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopOwnerRepository shopOwnerRepository;

    @Transactional
    public Shop updateShop(Shop newShop, String authorization) {
        Shop shop = getShopOwner(authorization).getShop();
        shop.update(newShop);
        return shop;
    }

    private ShopOwner getShopOwner(String authorization) {
        String username = new AuthHeaderParser(authorization).getUsername();

        return shopOwnerRepository.findByUsername(username);
    }
}
