package com.softuni.springintroex.domain.repositories;

import com.softuni.springintroex.domain.entities.AgeRestriction;
import com.softuni.springintroex.domain.entities.Author;
import com.softuni.springintroex.domain.entities.Book;
import com.softuni.springintroex.domain.entities.EditionType;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    Set<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    Set<Book> findAllByEditionTypeAndCopiesIsLessThan(EditionType editionType, int copies);

    Set<Book> findAllByPriceIsGreaterThanOrPriceIsLessThan(BigDecimal lower, BigDecimal higher);

    @Query("SELECT b FROM Book b WHERE SUBSTRING(b.releaseDate, 0, 4) NOT LIKE :year ")
    Set<Book> findAllByReleaseDateNotInYear(@Param(value = "year") String year);

    Set<Book> findAllByReleaseDateBefore(LocalDate date);

    Set<Book> findAllByAuthorLastNameStartingWith(String chars);

    @Query("SELECT count(b) FROM Book b WHERE length(b.title) > ?1 ")
    int countAllByTitleGreaterThan(int length);


    @Query("SELECT CONCAT(a.firstName,' ',a.lastName,' - ', SUM(b.copies)) " +
            " FROM Book b join Author a ON a.id = b.author.id " +
            "GROUP BY a.id")
    List<String> sumCopiesByAuthor();

    Book findByTitle(String title);
//UPDATE books b SET b.price = b.price + 100
//where B.release_date >

    @Transactional
    @Modifying
    @Query("UPDATE Book b SET b.price = b.copies + ?1 WHERE b.releaseDate > ?2 ")
    int updateCopiesCount(int copies, LocalDate localDate);

    @Transactional
    @Modifying
    @Query("DELETE FROM Book b WHERE b.copies < ?1 ")
    int deleteCopies(int number);
}
