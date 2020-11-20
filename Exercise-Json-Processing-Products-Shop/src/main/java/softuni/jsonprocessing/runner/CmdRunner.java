package softuni.jsonprocessing.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import softuni.jsonprocessing.service.CategoryService;
import softuni.jsonprocessing.service.ProductService;
import softuni.jsonprocessing.service.UserService;

import java.io.BufferedReader;

@Component
public class CmdRunner implements CommandLineRunner {
    private static final String WELCOME = "Welcome to another homework";
    private static final String ENTER_MSG = "Select option: Seed DB -> 0, query -> 1 to 4 or enter -> exit";

    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final BufferedReader bufferedReader;


    public CmdRunner(UserService userService,
                     ProductService productService,
                     CategoryService categoryService,
                     BufferedReader bufferedReader) {

        this.userService = userService;
        this.productService = productService;
        this.categoryService = categoryService;
        this.bufferedReader = bufferedReader;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(WELCOME);
        System.out.println(ENTER_MSG);
        String line;
        while (!(line = bufferedReader.readLine()).equalsIgnoreCase("exit")) {

            switch (line) {
                case "0":
                    seedDB();
                    break;
                case "1":
                    System.err.println(this.productService.executeQuery_1());
                    break;
                case "2":
                    System.out.println(this.userService.executeQuery2());
                    break;
                case "3":
                    System.out.println(this.categoryService.executeQuery3());
                    break;
                case "4":
                    System.out.println(this.userService.executeQuery4());
                    break;
                default:
                    System.out.println("No such task");
                    break;
            }
            System.out.println(ENTER_MSG);
        }
    }

    private void seedDB() throws Exception {
        System.out.println(this.userService.seedUsers());
        System.out.println(this.productService.seedProduct());
        System.out.println(this.categoryService.seedCategory());
    }

}
