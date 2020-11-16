package softuni.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import softuni.springintrousers.services.UserService;

public class RunApp implements CommandLineRunner {

    private final UserService userService;

    @Autowired
    public RunApp(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
            this.userService.seedUser();
    }
}
