package ru.k0r0tk0ff.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import ru.k0r0tk0ff.entity.Author;
import ru.k0r0tk0ff.service.AuthorService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

/**
 * Created by korotkov_a_a on 29.10.2018.
 */

@RestController
@AllArgsConstructor
@Api(tags = "AuthorController", description = "Контроллер для работы с сущностью Author")
public class AuthorController implements EntityController {

    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    private AuthorService authorService;

    @GetMapping(value = "/api/author/{id}")
    @ApiOperation(value = "Получить сущность 'Author' по его системному идентификатору")
    public ResponseEntity<Author> getEntityById(
            @NonNull
            @ApiParam(value = "Системный идентификатор сущности 'Author'", required = true)
            @PathVariable("id") Long id) {
        logger.info("Get author with id = {}", id);
        Optional<Author> author = authorService.getAuthorById(id);
        if (author.isEmpty()) logger.error("Author with id {} not found!", id);

        return author.isPresent() ? ok(author.get()) : noContent().build();
    }

    @DeleteMapping(value = "/api/author/{id}")
    @ApiOperation(value = "Удалить сущность 'Author' по его системному идентификатору")
    public ResponseEntity<String> delEntityById(
            @NonNull
            @ApiParam(value = "Системный идентификатор сущности 'Author'", required = true)
            @PathVariable("id") Long id) {
        if (!authorService.isAuthorExist(id)) {
            logger.error("Author does not exist!");
            return ResponseEntity.status(CONFLICT).body("Author does not exist!");
        }
        authorService.deleteAuthorById(id);

        return ok("Delete author success.");
    }

    @GetMapping(value = "/api/authors")
    @ApiOperation(value = "Получить все сущности 'Author'")
    public ResponseEntity<List<Author>> getAllEntities() {
        List<Author> authors = authorService.getAllAuthors();

        return authors.isEmpty() ? noContent().build() : ok(authors);
    }

    @PostMapping(value = "/api/author", consumes = "application/json;charset=UTF-8")
    @ApiOperation(value = "Создать сущность 'Author' в соответствии с полученными данными")
    public ResponseEntity<?> addEntity(
            @NonNull
            @ApiParam(value = "Данные для новой сущности 'Author'", required = true)
            @RequestBody Map<String, String> authorParameters) {
        if (authorService.isInputParameterNameValid(authorParameters)) {
            Author author = new Author(authorParameters.get("name"));
            if (authorService.isAuthorExist(author)) {
                logger.error("Cannot create author whith namr \"{}\", he (she) is exist! ", author.getName());
                return ResponseEntity.status(CONFLICT).body("Such an author exists!");
            }
            authorService.saveAuthor(author);

            return ok("Create author success.");
        } else return ResponseEntity.status(CONFLICT).body("Incorrect input.");
    }

    @PutMapping(value = "/api/author")
    @ApiOperation(value = "Обновление сущности 'Author'")
    public ResponseEntity<?> updateEntity(
            @NonNull
            @ApiParam(value = "Обновленные данные сущности 'Author'", required = true)
            @RequestBody Map<String, String> authorParameters) {
        if (authorService.isInputParametersForUpdateValid(authorParameters)) {
            if (!authorService.isAuthorExist(Long.parseLong(authorParameters.get("authorId")))) {
                logger.error("Author does not exist!");
                return new ResponseEntity<>("Author does not exist!", CONFLICT);
            }
            authorService.updateAuthor(authorParameters);

            return ok("Update author success.");
        } else return ResponseEntity.status(CONFLICT).body("Incorrect input.");
    }
}

