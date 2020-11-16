package softuni.springintro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import softuni.springintro.services.impl.BookServiceImpl;

import java.util.List;
import static softuni.springintro.constants.MagicStrings.SECOND_TASK;

@Component
@Order(value = 2)
public class RunSecondTask implements CommandLineRunner {
    private final BookServiceImpl bookService;

    @Autowired
    public RunSecondTask(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @Override
    public void run(String... args)  {
        System.err.println(SECOND_TASK);
        List<String> authors = this.bookService.findAllAuthors();
       authors.forEach(System.out::println);
    }
}
