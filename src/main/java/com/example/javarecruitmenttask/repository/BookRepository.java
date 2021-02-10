package com.example.javarecruitmenttask.repository;

import com.example.javarecruitmenttask.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    boolean existsByIsbn(String isbn);
}
