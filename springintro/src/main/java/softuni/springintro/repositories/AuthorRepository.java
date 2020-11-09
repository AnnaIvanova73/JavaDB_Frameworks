package softuni.springintro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.springintro.domain.entities.Author;

import java.util.List;

// пишем методи/ заявки / връзка с базата
@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {

    @Query("SELECT a FROM Author AS a ORDER BY a.books.size DESC ")
    List<Author> findAuthorByCountOfBooks();

    Author getByFirstNameAndLastName(String first, String last);
}
