package ru.k0r0tk0ff.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import ru.k0r0tk0ff.entity.Author;
import ru.k0r0tk0ff.repository.AuthorRepo;
import java.util.List;
import java.util.Optional;

/**
 * Created by korotkov_a_a on 29.10.2018.
 */

@Service
public class AuthorService {

    private AuthorRepo authorRepo;

    @Autowired
    public AuthorService(AuthorRepo authorRepo) {
        this.authorRepo = authorRepo;
    }

    public Optional<Author> getAuthorById(Long id) {
        return authorRepo.findById(id);
    }

    public List<Author> getAllAuthors() {
        return authorRepo.findAll();
    }

    public void saveAuthorToAuthorRepo(Author author) {
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
        Optional<Author> savedauthor = authorRepo.findOne(example);
        if (savedauthor.isPresent()) {
            author = savedauthor.get();
        } else {
            saveAuthorToAuthorRepo(author);
        }
        return author;
    }
}
