package softuni.springintro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import softuni.springintro.services.BookService;

import java.util.List;

import static softuni.springintro.constants.MagicStrings.FIRST_TASK;

@Component
@Order(value = 1)
public class RunFirstTask implements CommandLineRunner {
    private final BookService bookService;

    @Autowired
    public RunFirstTask(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) {
        System.err.println(FIRST_TASK);
        List<String> titles = this.bookService.findAllTitles();
        titles.forEach(System.out::println);

    }
}
