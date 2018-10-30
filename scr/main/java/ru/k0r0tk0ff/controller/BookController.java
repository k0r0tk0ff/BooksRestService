package ru.k0r0tk0ff.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.k0r0tk0ff.entity.Book;
import ru.k0r0tk0ff.service.BookService;

import java.util.List;

/**
 * Created by korotkov_a_a on 29.10.2018.
 */

/**
 * Created by korotkov_a_a on 29.10.2018.
 */

@RestController
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    private BookService bookService;

    @Autowired
    public BookController(BookService BookService) {
        this.bookService = BookService;
    }

    @RequestMapping(value = "/api/book/{id}", method = RequestMethod.GET)
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id){
        logger.info("Get book with id = {}", id);
        Book book = bookService.getBookById(id);
        if(book == null) {
            logger.error("book with id {} not found.", id);
            return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Book>(book, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/books", method = RequestMethod.GET)
    public ResponseEntity<List<Book>> listAllBooks() {
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}
