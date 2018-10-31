package ru.k0r0tk0ff.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "WISHLIST")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
    @JsonBackReference
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wishlist wishlist = (Wishlist) o;
        return book.equals(wishlist.book);
    }

    @Override
    public int hashCode() {
        return book.hashCode();
    }
}
