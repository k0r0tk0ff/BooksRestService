package ru.k0r0tk0ff.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.k0r0tk0ff.entity.Book;
import ru.k0r0tk0ff.service.BookService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

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
        Optional<Book> book = bookService.getBookById(id);
        if(book.isPresent()) {return new ResponseEntity<Book>(book.get(), HttpStatus.OK);}
        logger.error("Book with id = {} not found!", id);
        return new ResponseEntity<Book>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/api/books", method = RequestMethod.GET)
    public ResponseEntity<List<Book>> listAllBooks() {
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            logger.error("Datastore with books is empty!");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}
