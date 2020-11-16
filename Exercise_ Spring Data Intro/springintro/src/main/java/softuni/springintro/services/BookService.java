package softuni.springintro.services;

import java.io.IOException;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<String> findAllTitles();
    List<String> findAllAuthors();
    String getBooksByAuthor();
}
