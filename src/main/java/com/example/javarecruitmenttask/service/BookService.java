package com.example.javarecruitmenttask.service;

import com.example.javarecruitmenttask.exception.IsbnAlreadyExistException;
import com.example.javarecruitmenttask.model.Author;
import com.example.javarecruitmenttask.model.Book;
import com.example.javarecruitmenttask.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;
    private final AuthorService authorService;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    public void addBook(Book book) {

        Author author = book.getAuthor();

        logger.info("Add book to database: {"
                + "author=" + author.getForename() + " " + author.getSurname()
                + ", title=" + book.getTitle()
                + ", isbn=" + book.getIsbn() + "}");

        Author validatedAuthor = authorService.createAuthorIfNotExistsAndReturnIfValid(author);
        book.setAuthor(validatedAuthor);

        /* Unique constraints annotations do not allow to write validation message. Require to create own annotation but
         simplest solution is to throw exception with proper message. */
        if (bookRepository.existsByIsbn(book.getIsbn()))
            throw new IsbnAlreadyExistException("ISBN already exists for a specific book!");

        bookRepository.save(book);
    }

    public List<Book> getAllBooks() {

        List<Book> bookList = bookRepository.findAll();
        logger.info("Get all books: " + bookList);

        return bookList;
    }



}
