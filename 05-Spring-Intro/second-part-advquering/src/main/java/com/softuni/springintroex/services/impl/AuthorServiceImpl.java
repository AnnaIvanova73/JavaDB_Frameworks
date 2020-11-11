package com.softuni.springintroex.services.impl;

import com.softuni.springintroex.domain.entities.Author;
import com.softuni.springintroex.domain.repositories.AuthorRepository;
import com.softuni.springintroex.services.AuthorService;
import com.softuni.springintroex.utils.FileUtil;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.softuni.springintroex.constants.GlobalConstants.AUTHORS_FILE_PATH;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final FileUtil fileUtil;
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(FileUtil fileUtil, AuthorRepository authorRepository) {
        this.fileUtil = fileUtil;
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthorsInDB() throws IOException {

        String[] lines = this.fileUtil.readFileContent(AUTHORS_FILE_PATH);

        for (String line : lines) {
            String[] tokens = line.split("\\s+");
            Author author = new Author(tokens[0],tokens[1]);
            this.authorRepository.saveAndFlush(author);


        }
    }

    @Override
    public void printAllAuthorsNameEndingWith() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();
        this.authorRepository.findAllByFirstNameEndingWith(input)
                .forEach(a -> System.out.println(a.getFirstName() + " " + a.getLastName()));
    }
}
