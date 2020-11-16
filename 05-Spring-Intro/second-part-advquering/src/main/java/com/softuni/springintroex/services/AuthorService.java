package com.softuni.springintroex.services;


import java.io.IOException;

public interface AuthorService {
    void seedAuthorsInDB() throws IOException;

    void printAllAuthorsNameEndingWith() throws IOException;

    void getCountOfBooks() throws IOException;
}
