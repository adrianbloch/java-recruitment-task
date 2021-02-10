package com.example.javarecruitmenttask.repository;

import com.example.javarecruitmenttask.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Optional<Author> findByForenameAndSurname(String forename, String surname);
}
