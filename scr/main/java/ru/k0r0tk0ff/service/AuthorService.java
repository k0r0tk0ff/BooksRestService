package ru.k0r0tk0ff.service;

import org.springframework.beans.factory.annotation.Autowired;
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
}
