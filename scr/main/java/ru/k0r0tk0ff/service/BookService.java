package ru.k0r0tk0ff.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.k0r0tk0ff.entity.Book;
import ru.k0r0tk0ff.repository.BookRepo;
import java.util.List;
import java.util.Optional;

/**
 * Created by korotkov_a_a on 29.10.2018.
 */

@Service
public class BookService {

    private BookRepo bookRepo;

    @Autowired
    public BookService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepo.findById(id);
    }

    public List<Book> getAllBooks() {
        return this.bookRepo.findAll();
    }
}
