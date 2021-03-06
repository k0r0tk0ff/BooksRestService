package ru.k0r0tk0ff.web.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@AllArgsConstructor
public class BookController implements EntityController {

    private BookService bookService;

    @GetMapping(value = "/api/book/{id}")
    public ResponseEntity<?> getEntityById(@PathVariable("id") Long id) {
        log.info("Get book with id = {}", id);
        Optional<Book> book = bookService.getOptionalObjectForBookById(id);
        if (book.isPresent()) {
            Map<String, String> bookParameters = bookService.getBookParameters(id);
            return new ResponseEntity<>(bookParameters, HttpStatus.OK);
        }
        log.error("Book with id = {} not found!", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/api/book/{id}")
    public ResponseEntity<?> delEntityById(@PathVariable("id") Long id) {
        log.info("Delete book with id = {}", id);
        Map<String, String> results = bookService.delBookById(id);
        if (results.containsKey("SUCCESS")) {
            log.info(results.get("SUCCESS"));
            return new ResponseEntity<>(results.get("SUCCESS"), HttpStatus.OK);
        }
        log.error("Book with id = {} was not delete!", id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/api/books")
    public ResponseEntity<?> getAllEntities() {
        List<Map<String, String>> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            log.error("Datastore with books is empty!");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping("/api/book")
    public ResponseEntity<?> addEntity(@RequestBody Map<String, String> bookParameters) {
        Map<String, String> results = bookService.createBook(bookParameters);
        if (results.containsKey("SUCCESS")) {
            log.info("{} Book name = \"{}\" Author name = \"{}\"",
                    results.get("SUCCESS"),
                    bookParameters.get("name"),
                    bookParameters.get("authorName"));
            return new ResponseEntity<>(results.get("SUCCESS"), HttpStatus.OK);
        }
        log.error("FAIL! {}", results.get("FAIL"));
        return new ResponseEntity<>(results.get("FAIL"), HttpStatus.CONFLICT);
    }

    @PutMapping("/api/book")
    public ResponseEntity<?> updateEntity(@RequestBody Map<String, String> bookParameters) {
        Map<String, String> results = bookService.updateBook(bookParameters);
        if (results.containsKey("SUCCESS")) {
            log.info("{} Book name = \"{}\" Author id = \"{}\" price = \"{}\"",
                    results.get("SUCCESS"),
                    bookParameters.get("name"),
                    bookParameters.get("author_id"),
                    bookParameters.get("price"));
            return new ResponseEntity<>(results.get("SUCCESS"), HttpStatus.OK);
        }
        log.error("FAIL! {}", results.get("FAIL"));
        return new ResponseEntity<>(results.get("FAIL"), HttpStatus.CONFLICT);
    }
}
