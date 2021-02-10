package com.example.javarecruitmenttask.service;

import com.example.javarecruitmenttask.exception.InvalidAuthorException;
import com.example.javarecruitmenttask.model.Author;
import com.example.javarecruitmenttask.repository.AuthorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

// moved all author business logic to AuthorService because of Single Responsibility Principle
@Service
public class AuthorService {

    private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author createAuthorIfNotExistsAndReturnIfValid(Author author) {

        String authorForename = author.getForename();
        String authorSurname = author.getSurname();

        if (!checkIfAuthorsForenameOrSurnameStartsWithLetterA(authorForename, authorSurname))
            throw new InvalidAuthorException("Author's forename or surname must start with 'A'!");

        Optional<Author> authorByName = authorRepository.findByForenameAndSurname(authorForename, authorSurname);

        // avoid duplicate authors in database
        if (authorByName.isPresent()) {
            logger.info("Get author from database:{" + authorForename + " " + authorSurname + "}");
            return authorByName.get();
        } else {
            logger.info("Create new author:{" + authorForename + " " + authorSurname + "}");
            return authorRepository.save(author);
        }
    }

    private boolean checkIfAuthorsForenameOrSurnameStartsWithLetterA(String forename, String surname) {
        logger.info("Validate author's name: " + forename + " " + surname);
        String regex = "^A.*";

        return forename.matches(regex) || surname.matches(regex);
    }


}


