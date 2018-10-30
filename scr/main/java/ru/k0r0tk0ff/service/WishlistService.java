package ru.k0r0tk0ff.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.k0r0tk0ff.entity.Wishlist;
import ru.k0r0tk0ff.repository.WishlistRepo;

import java.util.List;
import java.util.Optional;

/**
 * Created by korotkov_a_a on 29.10.2018.
 */
@Service
public class WishlistService {

    private WishlistRepo wishlistRepo;

    @Autowired
    public WishlistService(WishlistRepo WishlistRepo) {
        this.wishlistRepo = WishlistRepo;
    }

    public Optional<Wishlist> getWishlistById(Long id) {
        return wishlistRepo.findById(id);
    }

    public List<Wishlist> getAllWishlists() {
        return this.wishlistRepo.findAll();
    }
}
