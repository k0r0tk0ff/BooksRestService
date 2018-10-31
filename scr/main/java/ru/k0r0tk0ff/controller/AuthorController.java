package ru.k0r0tk0ff.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.k0r0tk0ff.entity.Author;
import ru.k0r0tk0ff.service.AuthorService;

import java.util.List;
import java.util.Optional;

/**
 * Created by korotkov_a_a on 29.10.2018.
 */

@RestController
public class AuthorController {

    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    private AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping(value = "/api/authors")
    public ResponseEntity<List<Author>> listAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        if (authors.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping(value = "/api/author/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable("id") Long id){
        logger.info("Get author with id = {}", id);
        Optional<Author> author = authorService.getAuthorById(id);
        if(author.isPresent()) {return new ResponseEntity<>(author.get(), HttpStatus.OK);}
        logger.error("Author with id {} not found!", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/api/author")
    public ResponseEntity<Author> setAuthor(@PathVariable("id") Long id){
        logger.info("Create author with id = {}", id);
        Optional<Author> author = authorService.getAuthorById(id);
        if(author.isPresent()) {return new ResponseEntity<>(author.get(), HttpStatus.OK);}
        logger.error("Author with id {} not found!", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
