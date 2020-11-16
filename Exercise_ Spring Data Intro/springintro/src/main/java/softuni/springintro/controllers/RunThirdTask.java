package softuni.springintro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import softuni.springintro.services.impl.AuthorServiceImpl;

import static softuni.springintro.constants.MagicStrings.THIRD_TASK;

@Component
@Order(value = 3)
public class RunThirdTask implements CommandLineRunner {
    private final AuthorServiceImpl authorService;

    @Autowired
    public RunThirdTask(AuthorServiceImpl authorService) {
        this.authorService = authorService;
    }

    @Override
    public void run(String... args)  {
        System.err.println(THIRD_TASK);
        String asd = "Third task on the go";
        System.out.println(asd);
        System.out.println(this.authorService.getAuthorsByBookCount());
    }
}
