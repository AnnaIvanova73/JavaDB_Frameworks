package com.softuni.springintroex.domain.repositories;

import com.softuni.springintroex.domain.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.Set;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Set<Author> findAllByFirstNameEndingWith(String letters);

    @Transactional
    @Procedure(name = "find_count_books_by_name")
    Integer findTotalAmountOfBooksAuthor(@Param("first_name") String first_name,
                                         @Param("last_name") String last_name);
}
