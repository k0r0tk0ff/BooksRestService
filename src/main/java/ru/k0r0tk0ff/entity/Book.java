package ru.k0r0tk0ff.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by korotkov_a_a on 26.10.2018.
 */

@Entity
@Table(name = "BOOK")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "wishlist"})
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Book {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_id")
    private Long bookId;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @Column(name = "price")
    private Double price;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "book")
    @JoinColumn(name = "wishlist_id", referencedColumnName = "wishlist_id")
    @EqualsAndHashCode.Exclude
    private Wishlist wishlist;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn (name="author_id")
    @JsonBackReference
    private Author author;

}
