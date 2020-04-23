package ru.k0r0tk0ff.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.k0r0tk0ff.entity.Author;
import ru.k0r0tk0ff.repository.AuthorRepo;

/**
 * Класс для демонстрации использования библиотеки PowerMock
 * (см. https://www.baeldung.com/powermock-private-method)
 */
@PrepareForTest({AuthorRepo.class, AuthorService.class})
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
public class AuthorServiceTest {

    private AuthorRepo authorRepo;
    private AuthorService authorService;

    @Before
    public void setUp() {
        authorRepo = PowerMockito.mock(AuthorRepo.class);
        authorService = PowerMockito.spy(new AuthorService(authorRepo));
    }

    @Test
    public void saveAuthorToAuthorRepoTest() throws Exception {
        //Arrange
        String name = "Name";
        Author author = new Author(name);
        PowerMockito.when(authorRepo.save(author)).thenReturn(author);

        //Act
        authorService.createAuthorByName(name);

        //Assert
        //Проверяем вызов приватного метода
        PowerMockito.verifyPrivate(authorService).invoke("saveAuthorToAuthorRepo", author);
    }
}