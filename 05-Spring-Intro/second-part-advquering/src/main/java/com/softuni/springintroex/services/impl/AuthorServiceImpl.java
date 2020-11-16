package com.softuni.springintroex.services.impl;

import com.softuni.springintroex.domain.entities.Author;
import com.softuni.springintroex.domain.repositories.AuthorRepository;
import com.softuni.springintroex.services.AuthorService;
import com.softuni.springintroex.utils.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.IOException;

import static com.softuni.springintroex.constants.GlobalConstants.AUTHORS_FILE_PATH;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final FileUtil fileUtil;
    private final AuthorRepository authorRepository;
    private final BufferedReader reader;

    public AuthorServiceImpl(FileUtil fileUtil, AuthorRepository authorRepository, BufferedReader reader) {
        this.fileUtil = fileUtil;
        this.authorRepository = authorRepository;
        this.reader = reader;

    }

    @Override
    public void seedAuthorsInDB() throws IOException {

        String[] lines = this.fileUtil.readFileContent(AUTHORS_FILE_PATH);

        for (String line : lines) {
            String[] tokens = line.split("\\s+");
            Author author = new Author(tokens[0], tokens[1]);
            this.authorRepository.saveAndFlush(author);


        }
    }

    @Override
    public void printAllAuthorsNameEndingWith() throws IOException {
        System.out.println("Enter string:");
        String input = this.reader.readLine();
        this.authorRepository.findAllByFirstNameEndingWith(input)
                .forEach(a -> System.out.println(a.getFirstName() + " " + a.getLastName()));
    }

    @Override
    public void getCountOfBooks() throws IOException {
        System.out.println("Enter first and last name in one row with interval");
        String[] tokens = reader.readLine().split("\\s+");
        Integer count =
                this.authorRepository.findTotalAmountOfBooksAuthor(tokens[0], tokens[1]);
        String print =
                count == null ?
                        "No author in DB or No books for this author"
                        : String.format("%s %s has written %d books",tokens[0],tokens[1], count);
        System.err.println(print);

    }


}
