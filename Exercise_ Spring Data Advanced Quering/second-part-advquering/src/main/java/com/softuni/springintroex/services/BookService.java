package com.softuni.springintroex.services;

import java.io.IOException;

public interface BookService  {
    void seedBooksInDB() throws IOException;

    void task1printAllBooksTitleByAgeRes() throws IOException;
    void task2PrintAllBooksWithParams();
    void task3PrintAllBooksWithPriceLimits();
    void task4NotReleasedBooks() throws IOException;
    void task5PrintAllBookReleasedBefore() throws IOException;
    void task8BookTitlesSearch() throws IOException;
    void task9CountBooks() throws IOException;
    void task10TotalBookCopies();
    void task11ReducedBook() throws IOException;
    void task12IncreaseBookCopies() throws IOException;
    void task13RemoveBooks() throws IOException;
    void dropDB();
    String task7BooksSearch() throws IOException;
}
