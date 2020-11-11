package com.softuni.springintroex.services.impl;

import com.softuni.springintroex.domain.entities.*;
import com.softuni.springintroex.domain.repositories.AuthorRepository;
import com.softuni.springintroex.domain.repositories.BookRepository;
import com.softuni.springintroex.domain.repositories.CategoryRepository;
import com.softuni.springintroex.services.BookService;
import com.softuni.springintroex.utils.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;

    public BookServiceImpl(BookRepository bookRepository,
                           CategoryRepository categoryRepository,
                           AuthorRepository authorRepository,
                           FileUtil fileUtil) {
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
    public void printAllBooksTitleByAgeRes() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String type = reader.readLine().toUpperCase();
        AgeRestriction ageRestriction = AgeRestriction.valueOf(type);
        this.bookRepository.findAllByAgeRestriction(ageRestriction)
                .forEach(a -> System.out.println(a.getTitle()));
    }

    @Override
    public void printAllBooksWithParams() {
        this.bookRepository.findAllByEditionTypeAndCopiesIsLessThan(EditionType.GOLD, 5000)
                .forEach(a -> System.out.println(a.getTitle()));
    }

    @Override
    public void printAllBooksWithPriceLimits() {
        this.bookRepository.findAllByPriceIsGreaterThanOrPriceIsLessThan(new BigDecimal(40), new BigDecimal(5))
                .forEach(a -> System.out.println(a.getTitle() + " " + a.getPrice()));
    }

    @Override
    public void printAllBookTitlesNotInGivenYear() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();
        this.bookRepository.findAllByReleaseDateNotInYear(input)
                .forEach(a -> System.out.println(a.getTitle()));
    }

    @Override
    public void printAllBookReleasedBefore() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(input, dateTimeFormatter);
        this.bookRepository.findAllByReleaseDateBefore(localDate)
                .forEach(a -> System.out.println(a.getTitle() + a.getEditionType() + " " + a.getPrice()));
    }

    @Override
    public void printAllBooksWithAuthorLastNameStartWith() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();
        Set<Book> allByAuthorLastName = this.bookRepository.findAllByAuthorLastNameStartingWith(input);
        for (Book book : allByAuthorLastName) {
            System.out.printf("%s (%s %s)%n",book.getTitle(),
                    book.getAuthor().getFirstName(),
                    book.getAuthor().getLastName());
        }

    }

    @Override
    public void printCountBooksByLengthInput() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int input = Integer.parseInt(reader.readLine());
        int i = this.bookRepository.countAllByTitleGreaterThan(input);
        System.out.println(i);
    }

    @Override
    public void printAllAuthorWithTotalBookCopies() {
        List<String> maps = this.bookRepository.sumCopiesByAuthor();
        maps.forEach(System.out::println);
    }

    @Override
    public void printBookByTitle() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String s = reader.readLine();
        Book byTitle = this.bookRepository.findByTitle(s);
        System.out.println(s +
               " " +byTitle.getEditionType() + " " +
                byTitle.getAgeRestriction() + " " + byTitle.getPrice());
    }

    @Override
    public void printAllAddedCopies() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String date = reader.readLine();
        int copies = Integer.parseInt(reader.readLine());
        LocalDate localDate = LocalDate.parse(date,DateTimeFormatter.ofPattern("dd MMM yyyy"));
        System.out.println(this.bookRepository.updateCopiesCount(copies, localDate) * copies);
    }

    @Override
    public void deleteBooks() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int copies = Integer.parseInt(reader.readLine());
        System.out.println(this.bookRepository.deleteCopies(copies));
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
        int authorIndex = random.nextInt(authors.size()-1) + 1; // -1
        // в случаи, че се опита да вземе, от индекс 30, заради + 1;
        return authors.get(authorIndex);
    }
}
