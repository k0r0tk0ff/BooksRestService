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
import java.util.Map;
import java.util.Optional;

/**
 * Created by korotkov_a_a on 29.10.2018.
 */

@RestController
public class AuthorController implements EntityController {

    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    private AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping(value = "/api/author/{id}")
    public ResponseEntity<Author> getEntityById(@PathVariable("id") Long id){
        logger.info("Get author with id = {}", id);
        Optional<Author> author = authorService.getAuthorById(id);
        if(author.isPresent()) {return new ResponseEntity<Author>(author.get(), HttpStatus.OK);}
        logger.error("Author with id {} not found!", id);
        return new ResponseEntity<Author>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/api/author/{id}")
    public ResponseEntity<String> delEntityById(@PathVariable("id") Long id) {
        if (!authorService.isAuthorExist(id)) {
            logger.error("Author does not exist!");
            return new ResponseEntity<>("Author does not exist!", HttpStatus.CONFLICT);
        }
        authorService.deleteAuthorById(id);
        return new ResponseEntity<String>("Delete author success.", HttpStatus.OK);
    }

    @GetMapping(value = "/api/authors")
    public ResponseEntity<List<Author>> getAllEntities() {
        List<Author> authors = authorService.getAllAuthors();
        if (authors.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @PostMapping(value = "/api/author")
    public ResponseEntity<?> addEntity(@RequestBody Map<String, String> authorParameters) {
        if(authorService.isInputParameterNameValid(authorParameters)) {
        Author author = new Author(authorParameters.get("name"));
        if (authorService.isAuthorExist(author)) {
            logger.error("Author \"{}\" exist! ", author.getName());
            return new ResponseEntity<>("Author exist.", HttpStatus.CONFLICT);
        }
        authorService.saveAuthor(author);
        return new ResponseEntity<>("Create author success.", HttpStatus.OK);
        } else return new ResponseEntity<>("Incorrect input.", HttpStatus.CONFLICT);
    }

    @PutMapping(value = "/api/author")
    public ResponseEntity<?> updateEntity(@RequestBody Map<String, String> authorParameters) {
        if (authorService.isInputParametersForUpdateValid(authorParameters)) {
        if (!authorService.isAuthorExist(Long.parseLong(authorParameters.get("authorId")))) {
            logger.error("Author does not exist!");
            return new ResponseEntity<>("Author does not exist!", HttpStatus.CONFLICT);
        }
        authorService.updateAuthor(authorParameters);
        return new ResponseEntity<>("Update author success.", HttpStatus.OK);
        } else return new ResponseEntity<>("Incorrect input.", HttpStatus.CONFLICT);
    }
}

