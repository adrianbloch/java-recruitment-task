package com.example.javarecruitmenttask.service;

import com.example.javarecruitmenttask.exception.InvalidAuthorException;
import com.example.javarecruitmenttask.model.Author;
import com.example.javarecruitmenttask.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;
    @InjectMocks
    private AuthorService authorService;

    @Test
    void createAlreadyExistingAuthorWithForenameStartingWithLetterAReturnsAuthorFromDb() {

        //given
        var author = new Author(1, "Adam", "Mickiewicz", Collections.emptySet());

        var newAuthor = new Author();
        newAuthor.setForename("Adam");
        newAuthor.setSurname("Mickiewicz");

        given(authorRepository
                .findByForenameAndSurname(newAuthor.getForename(), newAuthor.getSurname()))
                .willReturn(Optional.of(author));

        //when
        Author resultAuthor = authorService.createAuthorIfNotExistsAndReturnIfValid(newAuthor);

        //then
        assertEquals(1, resultAuthor.getId());
        assertEquals("Adam", resultAuthor.getForename());
        assertEquals("Mickiewicz", resultAuthor.getSurname());
    }

    @Test
    void createAlreadyExistingAuthorWithSurnameStartingWithLetterAReturnsAuthorFromDb() {

        //given
        var author = new Author(1, "Jan", "Adamowski", Collections.emptySet());

        var newAuthor = new Author();
        newAuthor.setForename("Jan");
        newAuthor.setSurname("Adamowski");

        given(authorRepository
                .findByForenameAndSurname(newAuthor.getForename(), newAuthor.getSurname()))
                .willReturn(Optional.of(author));

        //when
        Author resultAuthor = authorService.createAuthorIfNotExistsAndReturnIfValid(newAuthor);

        //then
        assertEquals(1, resultAuthor.getId());
        assertEquals("Jan", resultAuthor.getForename());
        assertEquals("Adamowski", resultAuthor.getSurname());
    }

    @Test
    void createAlreadyExistingAuthorWithForenameAndSurnameStartingWithLetterAReturnsAuthorFromDb() {

        //given
        var author = new Author(1, "Adam", "Adamowski", Collections.emptySet());

        var newAuthor = new Author();
        newAuthor.setForename("Adam");
        newAuthor.setSurname("Adamowski");

        given(authorRepository
                .findByForenameAndSurname(newAuthor.getForename(), newAuthor.getSurname()))
                .willReturn(Optional.of(author));

        //when
        Author resultAuthor = authorService.createAuthorIfNotExistsAndReturnIfValid(newAuthor);

        //then
        assertEquals(1, resultAuthor.getId());
        assertEquals("Adam", resultAuthor.getForename());
        assertEquals("Adamowski", resultAuthor.getSurname());
    }

    @Test
    void createNotExistingAuthorWithForenameStartingWithLetterAReturnsThisAuthor() {

        //given
        var newAuthor = new Author();
        newAuthor.setForename("Adam");
        newAuthor.setSurname("Mickiewicz");

        given(authorRepository
                .findByForenameAndSurname(newAuthor.getForename(), newAuthor.getSurname()))
                .willReturn(Optional.empty());
        given(authorRepository
                .save(newAuthor))
                .willReturn(newAuthor);

        //when
        Author resultAuthor = authorService.createAuthorIfNotExistsAndReturnIfValid(newAuthor);

        //then
        assertEquals("Adam", resultAuthor.getForename());
        assertEquals("Mickiewicz", resultAuthor.getSurname());
    }

    @Test
    void createNotExistingAuthorWithSurnameStartingWithLetterAReturnsThisAuthor() {

        //given
        var newAuthor = new Author();
        newAuthor.setForename("Jan");
        newAuthor.setSurname("Adamowski");

        given(authorRepository
                .findByForenameAndSurname(newAuthor.getForename(), newAuthor.getSurname()))
                .willReturn(Optional.empty());
        given(authorRepository
                .save(newAuthor))
                .willReturn(newAuthor);

        //when
        Author resultAuthor = authorService.createAuthorIfNotExistsAndReturnIfValid(newAuthor);

        //then
        assertEquals("Jan", resultAuthor.getForename());
        assertEquals("Adamowski", resultAuthor.getSurname());
    }

    @Test
    void createAuthorWithForenameAndSurnameNotStartingWithLetterAThrowsException() {

        //given
        var newAuthor = new Author();
        newAuthor.setForename("Jan");
        newAuthor.setSurname("Kochanowski");

        //when
        var exception = catchThrowable(() -> authorService.createAuthorIfNotExistsAndReturnIfValid(newAuthor));

        // then
        assertThat(exception)
                .isInstanceOf(InvalidAuthorException.class)
                .hasMessageContaining("Author's forename or surname must start with 'A'!");
    }


    /* no tests for null, empty values because constraints provided valid values by annotations in entity class;
       no tests for BookService because there is no more business logic to test */

}