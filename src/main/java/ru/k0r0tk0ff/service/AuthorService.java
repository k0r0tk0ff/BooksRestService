package ru.k0r0tk0ff.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import ru.k0r0tk0ff.entity.Author;
import ru.k0r0tk0ff.repository.AuthorRepo;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by korotkov_a_a on 29.10.2018.
 */
@Service
@AllArgsConstructor
public class AuthorService {

    private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);
    private AuthorRepo authorRepo;

    public Optional<Author> getAuthorById(Long id) {
        Optional<Author> author = authorRepo.findById(id);

        return author;
    }

    public List<Author> getAllAuthors() {
        return authorRepo.findAll();
    }

    private void saveAuthorToAuthorRepo(Author author) {
        this.authorRepo.save(author);
    }

    public void createAuthorByName(String name) {
        Author author = new Author();
        author.setName(name);
        saveAuthorToAuthorRepo(author);
    }

    public boolean isAuthorExist(Author author) {
        Example<Author> example = Example.of(author);
        return this.authorRepo.exists(example);
    }

    public boolean isAuthorExist(Long authorId) {
        return authorRepo.existsById(authorId);
    }

    public boolean isAuthorExist(String authorName) {
        Author author = new Author(authorName);
        return isAuthorExist(author);
    }

    public Author getOrCreateAndGetAuthorByName(String authorName) {
        Author author = new Author(authorName);
        Example<Author> example = Example.of(author);
        Optional<Author> savedAuthor = authorRepo.findOne(example);
        if (savedAuthor.isPresent()) {
            author = savedAuthor.get();
        } else {
            saveAuthorToAuthorRepo(author);
        }
        return author;
    }

    public void saveAuthor(Author author) {
        saveAuthorToAuthorRepo(author);
        logger.info("Create author success. {}", author.toString());
    }

    public void updateAuthor(Map<String,String> authorParameteers) {
        Author authorFromRepo = this.authorRepo.getOne(Long.parseLong(authorParameteers.get("authorId")));
        authorFromRepo.setName(authorParameteers.get("name"));
        authorRepo.save(authorFromRepo);
    }

    public void deleteAuthorById(Long id) {
        this.authorRepo.delete(authorRepo.getOne(id));
    }

    public boolean isInputParametersForUpdateValid(Map<String, String> authorParameters) {
        return (
                authorParameters.containsKey("authorId") &&
                authorParameters.containsKey("name") &&
               !authorParameters.get("authorId").equals("") &&
               !authorParameters.get("name").equals("")
        );
    }

    public boolean isInputParameterNameValid(Map<String, String> authorParameters) {
        return (authorParameters.containsKey("name") &&
                !authorParameters.get("name").equals("")
        );
    }
}
