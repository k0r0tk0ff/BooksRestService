package ru.k0r0tk0ff.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by korotkov_a_a on 26.10.2018.
 */
@Entity
@Table(name = "AUTHOR")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Author {

    public Author(String name) {
        this.name = name;
    }

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Long authorId;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    private Set<Book> books = new HashSet<>();

}
