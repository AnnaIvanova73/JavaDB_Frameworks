package com.softuni.springintroex.controller;

import com.softuni.springintroex.services.AuthorService;
import com.softuni.springintroex.services.BookService;
import com.softuni.springintroex.services.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Engine implements CommandLineRunner {
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public Engine(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello, world");

        // this.bookService.printAllBooksTitleByAgeRes();
        //this.bookService.printAllBooksWithParams();
        //this.bookService.printAllBooksWithPriceLimits();
        //this.bookService.printAllBookTitlesNotInGivenYear();
        //this.bookService.printAllBookReleasedBefore();
        // this.authorService.printAllAuthorsNameEndingWith();

        this.bookService.deleteBooks();
        System.exit(0);

    }

    void seedData() throws IOException {
        this.categoryService.seedCategoriesInDB();
        this.authorService.seedAuthorsInDB();
        this.bookService.seedBooksInDB();
    }

}
