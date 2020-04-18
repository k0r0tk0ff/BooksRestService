package ru.k0r0tk0ff.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "WISHLIST")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Wishlist {

    public Wishlist(Long count, Book book) {
        this.count = count;
        this.book = book;
    }

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_id")
    private Long wishlistId;

    @Getter
    @Setter
    @Column(name = "count")
    private Long count;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id", referencedColumnName = "book_id")
    @JsonBackReference
    private Book book;

}
