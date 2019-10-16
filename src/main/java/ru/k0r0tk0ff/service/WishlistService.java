package ru.k0r0tk0ff.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.k0r0tk0ff.entity.Book;
import ru.k0r0tk0ff.entity.Wishlist;
import ru.k0r0tk0ff.repository.WishlistRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by korotkov_a_a on 29.10.2018.
 */
@Service
@AllArgsConstructor
public class WishlistService {

    private WishlistRepo wishlistRepo;
    private BookService bookService;

    public List<Map<String, String>> getAllWishlists() {
        List<Wishlist> rawWishLists = this.wishlistRepo.findAll();
        List<Map<String, String>> resultData = new ArrayList<>(rawWishLists.size());
        return rawWishLists
                .stream()
                .map(x -> getWishlistData(x.getWishlistId()))
                .collect(Collectors.toList());
    }

    public boolean doesWishlistExist(Long id) {
        return wishlistRepo.existsById(id);
    }

    public boolean doesBookExistByBookId(String bookId) {
        return bookService.doesBookExistByBookId(Long.parseLong(bookId));
    }

    public Map<String, String> getWishlistData(Long id) {
        Wishlist wishlist = wishlistRepo.getOne(id);
        Long bookId = wishlist.getBook().getBookId();
        Map<String, String> wishListData = bookService.getBookParameters(bookId);
        wishListData.put("wishlistId", String.valueOf(id));
        wishListData.put("count", String.valueOf(wishlist.getCount()));
        return wishListData;
    }

    public boolean isInputParameterBookIdValid(Map<String, String> wishlistParameters) {
        return (wishlistParameters.containsKey("bookId") &&
                !wishlistParameters.get("bookId").equals("")
        );
    }

    public boolean isInputParametersValid(Map<String, String> wishlistParameters) {
        return (wishlistParameters.containsKey("bookId") &&
                !wishlistParameters.get("bookId").equals("") &&
                wishlistParameters.containsKey("count") &&
                !wishlistParameters.get("count").equals("")
        );
    }

    public void incrementCountInWishlist(String bookIdValue) {
        Book book = bookService.getBookById(Long.parseLong(bookIdValue));
        Wishlist wishlist;
        if (book.getWishlist() != null) {
            wishlist = book.getWishlist();
        } else {
            wishlist = new Wishlist(0L, book);
        }
        Long count = wishlist.getCount();
        count++;
        wishlist.setCount(count);
        book.setWishlist(wishlist);
        bookService.saveBook(book);
    }

    public void updateWishlist(Map<String, String> wishlistParameters) {
        Long bookId = Long.parseLong(wishlistParameters.get("bookId"));
        Long count = Long.parseLong(wishlistParameters.get("count"));
        Book book = bookService.getBookById(bookId);
        Wishlist wishlist;
        if (book.getWishlist() != null) {
            wishlist = book.getWishlist();
        } else {
            wishlist = new Wishlist(0L, book);
        }
        wishlist.setCount(count);
        book.setWishlist(wishlist);
        bookService.saveBook(book);
    }

    public void deleteWishlistById(Long id) {
        wishlistRepo.deleteById(id);
    }
}
