package softuni.springintro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import softuni.springintro.services.impl.BookServiceImpl;

import static softuni.springintro.constants.MagicStrings.FOURTH_TASK;

@Component
@Order(value = 4)
public class RunLastTask implements CommandLineRunner {
    private final BookServiceImpl bookService;

    @Autowired
    public RunLastTask(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @Override
    public void run(String... args)  {
        System.err.println(FOURTH_TASK);
        System.out.println(this.bookService.getBooksByAuthor());
    }
}
