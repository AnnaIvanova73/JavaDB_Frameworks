package com.softuni.springintroex.services;

import java.io.IOException;

public interface BookService  {
    void seedBooksInDB() throws IOException;

    void printAllBooksTitleByAgeRes() throws IOException;
    void printAllBooksWithParams();
    void printAllBooksWithPriceLimits();
    void printAllBookTitlesNotInGivenYear() throws IOException;
    void printAllBookReleasedBefore() throws IOException;
    void printAllBooksWithAuthorLastNameStartWith() throws IOException;
    void printCountBooksByLengthInput() throws IOException;
    void printAllAuthorWithTotalBookCopies();
    void printBookByTitle () throws IOException;
    void printAllAddedCopies() throws IOException;
    void deleteBooks () throws IOException;
}
