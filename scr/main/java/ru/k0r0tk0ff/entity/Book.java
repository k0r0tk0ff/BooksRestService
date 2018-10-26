package ru.k0r0tk0ff.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * Created by korotkov_a_a on 26.10.2018.
 */

@Entity
@Table(name = "BOOK")
public class Book {

    public Book() {
    }

    public Book(String name, Author author) {
        this.name = name;
        this.author = author;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_id")
    private Long bookId;
    private String name;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "book")
    @JoinColumn(name = "wishlist_id", referencedColumnName = "wishlist_id")
    private Wishlist wishlist;

    @ManyToOne
    @JoinColumn (name="author_id")
    @JsonBackReference
    private Author author;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Wishlist getWishlist() {
        return wishlist;
    }

    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
    }
}
