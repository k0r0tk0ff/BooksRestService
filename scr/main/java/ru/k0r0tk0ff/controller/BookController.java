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
import java.util.Map;
import java.util.Optional;

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

    @GetMapping(value = "/api/book/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id){
        logger.info("Get book with id = {}", id);
        Optional<Book> book = bookService.getBookById(id);
        if(book.isPresent()) {return new ResponseEntity<>(book.get(), HttpStatus.OK);}
        logger.error("Book with id = {} not found!", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/api/book/{id}")
    public ResponseEntity<String> delBookById(@PathVariable("id") Long id){
        logger.info("Delete book with id = {}", id);
        Map<String, String> results = bookService.delBookById(id);
        if(results.containsKey("SUCCESS")) {
            logger.info(results.get("SUCCESS"));
            return new ResponseEntity<>(results.get("SUCCESS"), HttpStatus.OK);
        }
        logger.error("Book with id = {} was not delete!", id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/api/books")
    public ResponseEntity<List<Book>> listAllBooks() {
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            logger.error("Datastore with books is empty!");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping("/api/book")
    public ResponseEntity<?> createBook(@RequestBody Map<String, String> bookParameters) {
        Map<String, String> results = bookService.createBook(bookParameters);
        if(results.containsKey("SUCCESS")) {
            logger.info("{} Book name = \"{}\" Author id = \"{}\"",
                    results.get("SUCCESS"),
                    bookParameters.get("name"),
                    bookParameters.get("author_id"));
            return new ResponseEntity<>(results.get("SUCCESS"), HttpStatus.OK);
        }
            logger.error("FAIL! {}", results.get("FAIL"));
            return new ResponseEntity<>(results.get("FAIL"),HttpStatus.CONFLICT);
    }

    @PutMapping("/api/book")
    public ResponseEntity<?> updateBook(@RequestBody Map<String, String> bookParameters) {
        Map<String, String> results = bookService.updateBook(bookParameters);
        if(results.containsKey("SUCCESS")) {
            logger.info("{} Book name = \"{}\" Author id = \"{}\" price = \"{}\"",
                    results.get("SUCCESS"),
                    bookParameters.get("name"),
                    bookParameters.get("author_id"),
                    bookParameters.get("price"));
            return new ResponseEntity<>(results.get("SUCCESS"), HttpStatus.OK);
        }

        logger.error("FAIL! {}", results.get("FAIL"));
        return new ResponseEntity<>(results.get("FAIL"),HttpStatus.CONFLICT);
    }
}
