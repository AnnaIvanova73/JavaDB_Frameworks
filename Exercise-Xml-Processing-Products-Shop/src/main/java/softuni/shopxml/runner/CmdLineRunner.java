package softuni.shopxml.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import softuni.shopxml.services.CategoryService;
import softuni.shopxml.services.ProductService;
import softuni.shopxml.services.UserService;

import java.io.BufferedReader;

@Component
public class CmdLineRunner  implements CommandLineRunner {
    private static final String MSG_OPTIONS = "Enter 0 for seed DB, Enter between 1 and 4 for queries, Enter EXIT";
    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final BufferedReader bufferedReader;

    public CmdLineRunner(UserService userService,
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
        System.out.println(MSG_OPTIONS);

        String line;
        while (!(line = bufferedReader.readLine()).equalsIgnoreCase("exit")) {

            switch (line) {
                case "0":
                    seedDb();
                    break;
                case "1":
                    System.out.println(this.productService.executeQuery1());
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
            System.out.println(MSG_OPTIONS);

        }

        System.exit(0);
    }

    private void seedDb() throws Exception {
        System.out.println("That will take sometime unfortunately");
        System.out.println(this.userService.seedUsers());
        System.out.println(this.productService.seedProduct());
        System.out.println(this.categoryService.seedCategories());
        System.err.println("INSERT DONE");
    }
}
