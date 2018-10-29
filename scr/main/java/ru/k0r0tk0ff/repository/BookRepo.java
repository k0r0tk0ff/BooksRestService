package ru.k0r0tk0ff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.k0r0tk0ff.entity.Book;

/**
 * Created by korotkov_a_a on 29.10.2018.
 */

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {
}
