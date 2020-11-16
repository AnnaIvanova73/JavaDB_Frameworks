package com.softuni.springintroex.controller;

import com.softuni.springintroex.services.AuthorService;
import com.softuni.springintroex.services.BookService;
import com.softuni.springintroex.services.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;

@Component
public class Engine implements CommandLineRunner {
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final BufferedReader reader;

    public Engine(CategoryService categoryService,
                  AuthorService authorService,
                  BookService bookService,
                  BufferedReader reader) {

        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.reader = reader;

    }

    @Override
    public void run(String... args) throws Exception {

        String msg = String.format("If is needed seed the DB by entering seed ot drop it by entering drop%n:%n" +
                "Pick task between 1 or 14%n" +
                "Pick exit --> finish loop and drop db");

        System.err.println(msg);
        String input;
        while (!(input = reader.readLine().toLowerCase()).equals("exit")) {
            switch (input) {
                case "seed":
                    System.err.println("Please go to constants.GlobalConstants and change paths first");
                    seedData();
                    break;
                case "1":
                    this.bookService.task1printAllBooksTitleByAgeRes();
                    System.err.println(msg);
                    break;
                case "2":
                    this.bookService.task2PrintAllBooksWithParams();
                    System.err.println(msg);
                    break;
                case "3":
                    this.bookService.task3PrintAllBooksWithPriceLimits();
                    System.err.println(msg);
                    break;
                case "4":
                    this.bookService.task4NotReleasedBooks();
                    System.err.println(msg);
                    break;
                case "5":
                    this.bookService.task5PrintAllBookReleasedBefore();
                    System.err.println(msg);
                    break;
                case "6":
                    this.authorService.printAllAuthorsNameEndingWith();
                    System.err.println(msg);
                    break;
                case "7":
                    System.out.println(this.bookService.task7BooksSearch());
                    System.err.println(msg);
                    break;
                case "8":
                    this.bookService.task8BookTitlesSearch();
                    System.err.println(msg);
                    break;
                case "9":
                    this.bookService.task9CountBooks();
                    System.err.println(msg);
                    break;
                case "10":
                    this.bookService.task10TotalBookCopies();
                    System.err.println(msg);
                    break;
                case "11":
                    this.bookService.task11ReducedBook();
                    System.err.println(msg);
                    break;
                case "12":
                    this.bookService.task12IncreaseBookCopies();
                    System.err.println(msg);
                    break;
                case "13":
                    this.bookService.task13RemoveBooks();
                    break;
                case "14":
                    System.err.println("Check resources," +
                            " get script and create the procedure first");
                    this.authorService.getCountOfBooks();
                    break;
                case "drop":
                    this.bookService.dropDB();
                    break;
                default:
                    System.out.println("No such task");
                    break;
            }

        }


        System.exit(0);

    }

    void seedData() throws IOException {
        this.categoryService.seedCategoriesInDB();
        this.authorService.seedAuthorsInDB();
        this.bookService.seedBooksInDB();
    }

}
