package softuni.jsonprocessing.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.jsonprocessing.model.dto.export.query2.ProductExportDtoQuery2;
import softuni.jsonprocessing.model.dto.export.query2.UserExportDto;
import softuni.jsonprocessing.model.dto.export.query4.ProductsExportDtoQuery4;
import softuni.jsonprocessing.model.dto.export.query4.UserExportDtoQuery4;
import softuni.jsonprocessing.model.dto.importDto.UserImportDto;
import softuni.jsonprocessing.model.entities.Product;
import softuni.jsonprocessing.model.entities.User;
import softuni.jsonprocessing.repository.ProductRepository;
import softuni.jsonprocessing.repository.UserRepository;
import softuni.jsonprocessing.service.UserService;
import softuni.jsonprocessing.util.ValidatorUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final static String USERS_IMPORT_PATH = "src/main/resources/json-import/users.json";
    private static final String USERS_EXPORT_PATH_QUERY_2 = "src/main/resources/json-export/query2.json";
    private static final String USERS_EXPORT_PATH_QUERY_4 = "src/main/resources/json-export/query4.json";


    private final ModelMapper modelMapper;
    private final Gson gson;
    private final UserRepository userRepository;
    private final StringBuilder sb;
    private final ValidatorUtil validatorUtil;
    private final ProductRepository productRepository;


    @Autowired
    public UserServiceImpl(ModelMapper modelMapper,
                           Gson gson,
                           UserRepository userRepository,
                           StringBuilder sb,
                           ValidatorUtil validatorUtil,
                           ProductRepository productRepository) {

        this.modelMapper = modelMapper;
        this.gson = gson;
        this.userRepository = userRepository;
        this.sb = sb;
        this.validatorUtil = validatorUtil;
        this.productRepository = productRepository;

    }


    @Override
    public String seedUsers() throws IOException {
        this.sb.setLength(0);
        String source = String.join("", Files.readAllLines(Path.of(USERS_IMPORT_PATH)));

        UserImportDto[] userImportDtos = this.gson.fromJson(source, UserImportDto[].class);

        for (UserImportDto userImportDto : userImportDtos) {
            if (!validatorUtil.isValid(userImportDto)) {
                this.validatorUtil.violations(userImportDto)
                        .forEach(err -> sb.append(err.getMessage())
                                .append(String.format
                                        (" Error for input firstName: %s last name: %s age: %d",
                                                userImportDto.getFirstName(),
                                                userImportDto.getLastName(),
                                                userImportDto.getAge()))
                                .append(System.lineSeparator()));


            } else {
                User user = this.modelMapper.map(userImportDto, User.class);
                this.userRepository.saveAndFlush(user);
                sb.append("Successfully saved user")
                        .append(String.format("firstName: %s lastName: %s age: %s", user.getFirstName(),
                                user.getLastName(), user.getAge()))
                        .append(System.lineSeparator());
            }
        }

        List<User> all = this.userRepository.findAll();
        for (User user : all) {
            user.setFriends(getRandomFriends());
            this.userRepository.saveAndFlush(user);
        }

        return this.sb.toString().trim();
    }

    @Override
    public String executeQuery2() throws IOException {


        List<User> allUsers = this.userRepository.findInOrder();

        List<UserExportDto> dtosExportUsersObject = new ArrayList<>();

        for (User user : allUsers) {

            UserExportDto userDto = this.modelMapper.map(user, UserExportDto.class);
            List<ProductExportDtoQuery2> dtosExportProductObject = new ArrayList<>();
            List<Product> productEntity = this.productRepository.findAllBySeller(user.getId());

            if (productEntity.isEmpty()) {
                continue;
            }

            for (Product product : productEntity) {

                ProductExportDtoQuery2 dtoProduct = new ProductExportDtoQuery2(
                        product.getName(), product.getPrice(), product.getBuyer().getFirstName(),
                        product.getBuyer().getLastName()
                );
                dtosExportProductObject.add(dtoProduct);
            }

            userDto.setProducts(dtosExportProductObject);
            dtosExportUsersObject.add(userDto);
        }

        System.out.println(this.gson.toJson(dtosExportUsersObject));
        Writer writer = new FileWriter(USERS_EXPORT_PATH_QUERY_2);
        this.gson.toJson(dtosExportUsersObject, writer);
        writer.flush();
        writer.close();
        return "Finished query2!";
    }

    @Override
    public String executeQuery4() throws IOException {
        List<User> all = this.userRepository.findAll();
        List<UserExportDtoQuery4> export = new ArrayList<>();
        for (User user : all) {

            List<Product> entities = this.productRepository.findAllBySeller(user.getId());
            List<ProductsExportDtoQuery4> dtoProduct = new ArrayList<>();
            if(entities.isEmpty()){
                continue;
            }
            for (Product entity : entities) {
                ProductsExportDtoQuery4 map =
                        this.modelMapper.map(entity, ProductsExportDtoQuery4.class);
                dtoProduct.add(map);
            }
            UserExportDtoQuery4 dtoUserAndProducts =
                    this.modelMapper.map(user,UserExportDtoQuery4.class);
            dtoUserAndProducts.setSoldProducts(dtoProduct.size());

            dtoUserAndProducts.setProductsExportDtoQuery4(dtoProduct);
            export.add(dtoUserAndProducts);
        }

        List<UserExportDtoQuery4> collectExport = export.stream()
                .sorted((a, b) -> b.getSoldProducts() - (a.getSoldProducts()))
                .collect(Collectors.toList());

        System.out.println(this.gson.toJson(collectExport));
        Writer writer = new FileWriter(USERS_EXPORT_PATH_QUERY_4);
        this.gson.toJson(collectExport,writer);
        writer.flush();
        writer.close();
        return "Query 4 finish";
    }

    private Set<User> getRandomFriends() {
        List<User> products = this.userRepository.findAll();
        int seed = 3;
        Collections.shuffle(products, new Random(seed));
        return new HashSet<>(products).stream().limit(3).collect(Collectors.toSet());
    }
}
