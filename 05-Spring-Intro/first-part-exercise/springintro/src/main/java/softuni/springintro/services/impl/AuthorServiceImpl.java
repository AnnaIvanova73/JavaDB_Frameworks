package softuni.springintro.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.springintro.domain.entities.Author;
import softuni.springintro.domain.repositories.AuthorRepository;
import softuni.springintro.domain.repositories.BookRepository;
import softuni.springintro.services.AuthorService;
import softuni.springintro.util.FileUtil;

import java.io.IOException;

import static softuni.springintro.constants.Paths.AUTHOR_FILE_PATH;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;
    private final BookRepository bookRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, FileUtil fileUtil, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
        this.bookRepository = bookRepository;
    }

    @Override
    public void seedAuthors() throws IOException {
        String[] authors = this.fileUtil.fileContent(AUTHOR_FILE_PATH);
        //todo ако авторите се съдържат в базата по ид,
        // ако ги има в базата добавяме иначе не


        if (this.authorRepository.count() != 0){
            return;
        }
        for (String token : authors) {
            String[] params = token.split("\\s+");
            Author author = new Author();
            author.setFirstName(params[0]);
            author.setLastName(params[1]);

            this.authorRepository.saveAndFlush(author);
        }
    }

    @Override
    public String getAuthorsByBookCount() {
        StringBuilder builder = new StringBuilder();
         this.authorRepository.findAuthorByCountOfBooks().forEach(a -> builder
                 .append(String.format("Author: %s %s Books: %d %n"
                         ,a.getFirstName(),a.getLastName(),a.getBooks().size())));
                return builder.toString().trim();
    }

}
