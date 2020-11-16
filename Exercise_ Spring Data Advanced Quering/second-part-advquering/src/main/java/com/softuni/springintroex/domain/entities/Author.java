package com.softuni.springintroex.domain.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "authors")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "find_count_books_by_name",
                procedureName = "find_count_books_by_name",
                resultClasses = {Author.class},
                parameters = {
                        @StoredProcedureParameter(name = "first_name", type = String.class, mode = ParameterMode.IN),
                        @StoredProcedureParameter(name = "last_name", type = String.class, mode = ParameterMode.IN),
                        @StoredProcedureParameter(name = "count_books", type = Integer.class, mode = ParameterMode.OUT),
                })
})
public class Author extends BaseEntity {

    private String firstName;
    private String lastName;
    private Set<Book> books;

    public Author() {
    }

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @OneToMany(mappedBy = "author")
    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
