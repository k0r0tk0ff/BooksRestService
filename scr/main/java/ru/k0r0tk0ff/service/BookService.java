package ru.k0r0tk0ff.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import ru.k0r0tk0ff.entity.Book;
import ru.k0r0tk0ff.repository.BookRepo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by korotkov_a_a on 29.10.2018.
 */

@Service
public class BookService {
    private static final Map<String, String> SUCCESS_UPDATED = new HashMap<String, String>() {
        { put("SUCCESS", "The book was successfully updated.");}
    };
    private static final Map<String, String> SUCCESS_BOOK_WAS_DELETED = new HashMap<String, String>() {
        { put("SUCCESS", "The book was successfully deleted.");}
    };

    private static final Map<String, String> SUCCESS_SAVED = new HashMap<String, String>() {
        { put("SUCCESS", "The book was successfully saved.");}
    };
    private static final Map<String, String> FAIL_AUTHOR_NOT_EXIST = new HashMap<String, String>() {
        { put("FAIL", "The author does not exist! Check parameter \"author_id\"");}
    };
    private static final Map<String, String> FAIL_BOOK_EXIST = new HashMap<String, String>() {
        { put("FAIL", "The book exist!");}
    };
    private static final Map<String, String> FAIL_BOOK_NOT_EXIST = new HashMap<String, String>() {
        { put("FAIL", "The book not exist!");}
    };

    private BookRepo bookRepo;
    private AuthorService authorService;

    @Autowired
    public BookService(BookRepo bookRepo, AuthorService authorService) {
        this.bookRepo = bookRepo;
        this.authorService = authorService;
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepo.findById(id);
    }

    public List<Book> getAllBooks() {
        return this.bookRepo.findAll();
    }

    private boolean isBookExist(Book book) {
        Example<Book> example = Example.of(book);
        return this.bookRepo.findOne(example).isPresent();
    }

    private boolean isBookExist(Long id) {
        return this.bookRepo.existsById(id);
    }

    private void saveBookInBookRepo(Book book) {
        this.bookRepo.save(book);
    }

    public Map<String, String> createBook(Map<String, String> bookParameters) {
        Book book = bookBuilder(bookParameters);
        if(isBookExist(book)) {
            return FAIL_BOOK_EXIST;
        }
        if(!authorService.isAuthorExist(bookParameters.get("author_name"))) {
            authorService.createAuthorByName(bookParameters.get("author_name"));
        }
        book.setBookId((long) (bookRepo.findAll().lastIndexOf(book)));
        saveBookInBookRepo(book);
        return SUCCESS_SAVED;
    }

    public Map<String,String> updateBook(Map<String, String> bookParameters) {
        if(!authorService.isAuthorExist(bookParameters.get("author_name"))){
            return FAIL_AUTHOR_NOT_EXIST;
        }
        if(!isBookExist(Long.parseLong(bookParameters.get("book_id")))) {
            return FAIL_BOOK_NOT_EXIST;
        }
        Book book = bookBuilder(bookParameters);
        book.setBookId(Long.parseLong(bookParameters.get("book_id")));
        this.bookRepo.save(book);
        return SUCCESS_UPDATED;
    }

    private Book bookBuilder(Map<String, String> bookParameters) {
        String name = bookParameters.get("name");
        String price = bookParameters.get("price");
        String authorName = bookParameters.get("author_name");
        Book book = new Book();
        book.setAuthor(authorService.getOrCreateAndGetAuthorByName(authorName));
        book.setName(name);
        book.setPrice(Double.parseDouble(price));
        return book;
    }

    public Map<String, String> delBookById(Long id) {
        if(getBookById(id).isPresent()) {
            Book book = getBookById(id).get();
            bookRepo.delete(book);
        } else {
            return FAIL_BOOK_NOT_EXIST;
        }
        return SUCCESS_BOOK_WAS_DELETED;
    }

    public Map<String,String> getBookParameters(Long id) {
        Map<String, String> bookParameters = new HashMap<>(4);
        Book book = this.bookRepo.getOne(id);
        bookParameters.put("book_id", String.valueOf(id));
        bookParameters.put("name", book.getName());
        bookParameters.put("price", String.valueOf(book.getPrice()));
        bookParameters.put("author_name", book.getAuthor().getName());
        return bookParameters;
    }
}
