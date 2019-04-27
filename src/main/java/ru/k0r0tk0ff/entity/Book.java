package ru.k0r0tk0ff.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by korotkov_a_a on 26.10.2018.
 */

@Entity
@Table(name = "BOOK")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "wishlist"})
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

    @Column(name = "price")
    private Double price;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (!name.equals(book.name)) return false;
        if (!price.equals(book.price)) return false;
        return author.equals(book.author);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + author.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", wishlistId=" + wishlist.getWishlistId() +
                ", authorName=" + author.getName() +
                '}';
    }
}
