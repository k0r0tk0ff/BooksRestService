package ru.k0r0tk0ff.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.k0r0tk0ff.entity.Author;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AuthorRepoTest {

    @Autowired
    private AuthorRepo authorRepo;

    @Test
    public void getById() {
        Long testAuthorId = 10L;
        Author author = new Author();
        author.setName("TestAuthorName");
        author.setAuthorId(testAuthorId);
        authorRepo.save(author);
        Assert.assertNotNull(authorRepo.findById(testAuthorId));
    }
}