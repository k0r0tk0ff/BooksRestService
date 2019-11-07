package ru.k0r0tk0ff.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import ru.k0r0tk0ff.entity.Book;
import ru.k0r0tk0ff.repository.BookRepo;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by korotkov_a_a on 29.10.2018.
 */

@Service
@AllArgsConstructor
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

    public Optional<Book> getOptionalObjectForBookById(Long id) {
        return bookRepo.findById(id);
    }

    public Book getBookById(Long id) {
        return bookRepo.getOne(id);
    }

    public List<Map<String,String>> getAllBooks() {
        List<Book> books = this.bookRepo.findAll();
        List<Map<String,String>> list;
        list = books
                .stream()
                .map(x -> getBookParameters(x.getBookId()))
                .collect(Collectors.toList());
        return list;
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
        if(!authorService.isAuthorExist(bookParameters.get("authorName"))) {
            authorService.createAuthorByName(bookParameters.get("authorName"));
        }
        saveBookInBookRepo(book);
        return SUCCESS_SAVED;
    }

    public Map<String,String> updateBook(Map<String, String> bookParameters) {
        if(!authorService.isAuthorExist(bookParameters.get("authorName"))){
            return FAIL_AUTHOR_NOT_EXIST;
        }
        if(!isBookExist(Long.parseLong(bookParameters.get("bookId")))) {
            return FAIL_BOOK_NOT_EXIST;
        }
        Book book = bookBuilder(bookParameters);
        book.setBookId(Long.parseLong(bookParameters.get("bookId")));
        this.bookRepo.save(book);
        return SUCCESS_UPDATED;
    }

    private Book bookBuilder(Map<String, String> bookParameters) {
        String name = bookParameters.get("name");
        String price = bookParameters.get("price");
        String authorName = bookParameters.get("authorName");
        Book book = new Book();
        book.setAuthor(authorService.getOrCreateAndGetAuthorByName(authorName));
        book.setName(name);
        book.setPrice(Double.parseDouble(price));
        return book;
    }

    public Map<String, String> delBookById(Long id) {
        if(getOptionalObjectForBookById(id).isPresent()) {
            Book book = getOptionalObjectForBookById(id).get();
            bookRepo.delete(book);
        } else {
            return FAIL_BOOK_NOT_EXIST;
        }
        return SUCCESS_BOOK_WAS_DELETED;
    }

    public Map<String,String> getBookParameters(Long id) {
        Map<String, String> bookParameters = new HashMap<>(4);
        Book book = this.bookRepo.getOne(id);
        bookParameters.put("bookId", String.valueOf(id));
        bookParameters.put("name", book.getName());
        bookParameters.put("price", String.valueOf(book.getPrice()));
        bookParameters.put("authorName", book.getAuthor().getName());
        return bookParameters;
    }

    public boolean doesBookExistByBookId(Long bookId) {
        return bookRepo.existsById(bookId);
    }

    public void saveBook(Book book) {
        this.bookRepo.save(book);
    }
}
