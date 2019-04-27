package ru.k0r0tk0ff.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.k0r0tk0ff.service.WishlistService;

import java.util.List;
import java.util.Map;

/**
 * Created by korotkov_a_a on 29.10.2018.
 */

@RestController
public class WishlistController implements EntityController{

    private static final Logger logger = LoggerFactory.getLogger(WishlistController.class);
    private WishlistService wishlistService;

    @Autowired
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping(value = "/api/wishlist/{id}")
    public ResponseEntity<?> getEntityById(@PathVariable("id") Long id) {
        logger.info("Get wishlist with id = {}", id);
        if(!wishlistService.doesWishlistExist(id)){
            logger.error("Wishlist with id = {} not found!", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Map<String,String> wishlistExtendData = wishlistService.getWishlistData(id);
        return new ResponseEntity<>(wishlistExtendData, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/wishlist/{id}")
    public ResponseEntity<?> delEntityById(@PathVariable("id") Long id){
        if(!wishlistService.doesWishlistExist(id)) {
            logger.error("Wishlist with id = \"{}\" does not exist!", id);
            return new ResponseEntity<>("Wishlist does not exist!", HttpStatus.NOT_FOUND);
        }
        wishlistService.deleteWishlistById(id);
        logger.info("Wishlist with id = \"{}\" was successfully delete.", id);
        return new ResponseEntity<>("Delete wishlist success.", HttpStatus.OK);
    }

    @GetMapping(value = "/api/wishlists")
    public ResponseEntity<?> getAllEntities() {
        List<Map<String,String>> wishlists = wishlistService.getAllWishlists();
        if (wishlists.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(wishlists, HttpStatus.OK);
    }

    @PostMapping(value = "/api/wishlist")
    public ResponseEntity<?> addEntity(@RequestBody Map<String, String> wishlistParameters) {

        if(wishlistService.isInputParameterBookIdValid(wishlistParameters)) {
            String bookIdValue = wishlistParameters.get("bookId");
            if (wishlistService.doesBookExistByBookId(bookIdValue)) {
                wishlistService.incrementCountInWishlist(bookIdValue);
                logger.info("Increment count for book with bookId = \"{}\" success.", bookIdValue);
                return new ResponseEntity<>("Add book to wishlist success.", HttpStatus.OK);
            }
            return new ResponseEntity<>("Book not found.", HttpStatus.NOT_FOUND);
        } else return new ResponseEntity<>("Incorrect input.", HttpStatus.CONFLICT);
    }

    @PutMapping(value = "/api/wishlist")
    public ResponseEntity<?> updateEntity(@RequestBody Map<String, String> wishlistParameters) {
        if(wishlistService.isInputParametersValid(wishlistParameters)) {
            String bookIdValue = wishlistParameters.get("bookId");
            if (wishlistService.doesBookExistByBookId(bookIdValue)) {
                wishlistService.updateWishlist(wishlistParameters);
                logger.info("Update count for book with bookId = \"{}\" success.", bookIdValue);
                return new ResponseEntity<>("Update book success.", HttpStatus.OK);
            }
            return new ResponseEntity<>("Book not found.", HttpStatus.NOT_FOUND);
        } else return new ResponseEntity<>("Incorrect input.", HttpStatus.CONFLICT);
    }
}
