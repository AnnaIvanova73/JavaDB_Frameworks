package softuni.springintro.services;

import softuni.springintro.domain.entities.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {
    void seedAuthors() throws IOException;

    String getAuthorsByBookCount();
}
