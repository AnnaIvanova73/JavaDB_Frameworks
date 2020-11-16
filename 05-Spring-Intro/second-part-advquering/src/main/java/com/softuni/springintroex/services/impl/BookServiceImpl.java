package com.softuni.springintroex.services.impl;

import com.softuni.springintroex.domain.entities.*;
import com.softuni.springintroex.domain.repositories.AuthorRepository;
import com.softuni.springintroex.domain.repositories.BookRepository;
import com.softuni.springintroex.domain.repositories.CategoryRepository;
import com.softuni.springintroex.services.BookService;
import com.softuni.springintroex.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.softuni.springintroex.constants.GlobalConstants.BOOKS_FILE_PATH;

@Service
public class BookServiceImpl implements BookService {

    private final BufferedReader reader;
    private final StringBuilder builder;
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;

    @Autowired
    public BookServiceImpl(BufferedReader reader,
                           StringBuilder builder,
                           BookRepository bookRepository,
                           CategoryRepository categoryRepository,
                           AuthorRepository authorRepository,
                           FileUtil fileUtil) {
        this.reader = reader;
        this.builder = builder;
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;

    }


    @Transactional
    @Override
    public void seedBooksInDB() throws IOException {

        String[] lines = this.fileUtil.readFileContent(BOOKS_FILE_PATH);

        for (String line : lines) {
            String[] tokens = line.split("\\s+");
            Book book = new Book();

            EditionType editionType = EditionType.values()[Integer.parseInt(tokens[0])];
            ;
            book.setEditionType(editionType);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate localDate = LocalDate.parse(tokens[1], formatter);
            book.setReleaseDate(localDate);

            int copies = Integer.parseInt(tokens[2]);
            book.setCopies(copies);


            BigDecimal price = new BigDecimal(tokens[3]);
            book.setPrice(price);

            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(tokens[4])];
            book.setAgeRestriction(ageRestriction);


            StringBuilder titleBuilder = new StringBuilder();
            for (int i = 5; i < tokens.length; i++) {
                titleBuilder.append(tokens[i]).append(" ");
            }
            String title = titleBuilder.toString().trim();
            book.setTitle(title);


            book.setAuthor(setRandomAuthor());

            book.setCategories(setRandomCategories());
            this.bookRepository.saveAndFlush(book);
        }


    }

    @Override
    public void task1printAllBooksTitleByAgeRes() throws IOException {
        System.out.println("Enter age restriction: ");
        String type = this.reader.readLine().toUpperCase().trim();
        AgeRestriction ageRestriction = AgeRestriction.valueOf(type);
        this.bookRepository.findAllByAgeRestriction(ageRestriction)
                .forEach(a -> System.out.println(a.getTitle()));
    }

    @Override
    public void task2PrintAllBooksWithParams() {
        this.bookRepository.findAllByEditionTypeAndCopiesIsLessThan(EditionType.GOLD, 5000)
                .forEach(a -> System.out.println(a.getTitle()));
    }

    @Override
    public void task3PrintAllBooksWithPriceLimits() {
        this.bookRepository.findAllByPriceIsGreaterThanOrPriceIsLessThan(new BigDecimal(40), new BigDecimal(5))
                .forEach(a -> System.out.println(a.getTitle() + " " + a.getPrice()));
    }

    @Override
    public void task4NotReleasedBooks() throws IOException {
        System.out.println("Enter year:");
        String input = reader.readLine().trim();
        this.bookRepository.findAllByReleaseDateNotInYear(input)
                .forEach(a -> System.out.println(a.getTitle()));
    }

    @Override
    public void task5PrintAllBookReleasedBefore() throws IOException {
        System.out.println("Enter date in format yy-mm-yyyy:");
        String input = reader.readLine().trim();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(input, dateTimeFormatter);
        this.bookRepository.findAllByReleaseDateBefore(localDate)
                .forEach(a -> System.out.println(a.getTitle() + a.getEditionType() + " " + a.getPrice()));
    }

    @Override
    public String task7BooksSearch() throws IOException {
        builder.setLength(0);
        System.out.println("Search books by enter string: ");
        String string = reader.readLine().trim().toLowerCase();
        this.bookRepository.findAllByTitleIsContaining(string)
                .forEach(t -> builder.append(t.getTitle()).append(System.lineSeparator()));
        return builder.toString().trim();
    }

    @Override
    public void task8BookTitlesSearch() throws IOException {
        System.out.println("Search book titles and author by enter chars to match last name:");
        String input = reader.readLine().trim();
        Set<Book> allByAuthorLastName = this.bookRepository.findAllByAuthorLastNameStartingWith(input);
        for (Book book : allByAuthorLastName) {
            System.out.printf("%s (%s %s)%n", book.getTitle(),
                    book.getAuthor().getFirstName(),
                    book.getAuthor().getLastName());
        }

    }

    @Override
    public void task9CountBooks() throws IOException {
        System.out.println("Enter number:");
        int input = Integer.parseInt(reader.readLine().trim());
        int countBooks = this.bookRepository.countAllByTitleGreaterThan(input);
        System.out.printf("Count books: %d%n",countBooks);
    }

    @Override
    public void task10TotalBookCopies() {
        List<String> maps = this.bookRepository.sumCopiesByAuthor();
        maps.forEach(System.out::println);
    }

    @Override
    public void task11ReducedBook() throws IOException {
        System.out.println("Enter title:");
        String s = reader.readLine().trim();
        Book byTitle = this.bookRepository.findByTitle(s);
        System.out.println(s +
                " " + byTitle.getEditionType() + " " +
                byTitle.getAgeRestriction() + " " + byTitle.getPrice());
    }

    @Override
    public void task12IncreaseBookCopies() throws IOException {
        System.out.println("Enter date and copies");
        String date = reader.readLine().trim();
        int copies = Integer.parseInt(reader.readLine().trim());
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd MMM yyyy"));
        System.out.printf("Total number of books: %d%n",
                this.bookRepository.updateCopiesCount(copies, localDate) * copies);
    }

    @Override
    public void task13RemoveBooks() throws IOException {
        System.out.println("Enter copies");
        int copies = Integer.parseInt(reader.readLine().trim());
        System.out.printf("Removed: %d",this.bookRepository.deleteCopies(copies));
    }

    @Override
    public void dropDB() {
        this.bookRepository.dropDB();

    }


    private Set<Category> setRandomCategories() {
        Set<Category> categories = new HashSet<>();
        List<Category> all = this.categoryRepository.findAll();

        for (int i = 0; i < 3; i++) {
            categories.add(all.get(i));
        }

        return categories;
    }


    private Author setRandomAuthor() {
        Random random = new Random();
        List<Author> authors = this.authorRepository.findAll();
        int authorIndex = random.nextInt(authors.size() - 1) + 1;
        return authors.get(authorIndex);
    }


}
