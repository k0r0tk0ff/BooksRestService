package ru.k0r0tk0ff.entity;

import javax.persistence.*;

@Entity
@Table(name = "WISHLIST")
public class Wishlist {

    public Wishlist() {
    }

    public Wishlist(Long count, Book book) {
        this.count = count;
        this.book = book;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "wishlist_id")
    private Long wishlistId;

    @Column(name = "count")
    private Long count;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id", referencedColumnName = "book_id")
    private Book book;

    public Long getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(Long wishlistId) {
        this.wishlistId = wishlistId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
