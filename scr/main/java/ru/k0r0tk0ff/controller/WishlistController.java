package ru.k0r0tk0ff.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.k0r0tk0ff.entity.Wishlist;
import ru.k0r0tk0ff.service.WishlistService;

import java.util.List;
import java.util.Optional;

/**
 * Created by korotkov_a_a on 29.10.2018.
 */

@RestController
public class WishlistController {

    private static final Logger logger = LoggerFactory.getLogger(WishlistController.class);

    private WishlistService wishlistService;

    @Autowired
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @RequestMapping(value = "/api/wishlists", method = RequestMethod.GET)
    public ResponseEntity<List<Wishlist>> listAllWishlists() {
        List<Wishlist> wishlists = wishlistService.getAllWishlists();
        if (wishlists.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Wishlist>>(wishlists, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/wishlist/{id}", method = RequestMethod.GET)
    public ResponseEntity<Wishlist> getWishlistById(@PathVariable("id") Long id){
        logger.info("Get wishlist with id = {}", id);
        Optional<Wishlist> wishlist = wishlistService.getWishlistById(id);
        if(wishlist.isPresent()) {return new ResponseEntity<Wishlist>(wishlist.get(), HttpStatus.OK);}
        logger.error("Wishlist with id = {} not found!", id);
        return new ResponseEntity<Wishlist>(HttpStatus.NO_CONTENT);
    }
}
