package softuni.shopxml.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.shopxml.domain.dto.export.query2.UserExportDtoQuery;
import softuni.shopxml.domain.dto.export.query2.UserRootExportDtoQuery2;
import softuni.shopxml.domain.dto.export.query4.ProductRootExportDtoQuery4;
import softuni.shopxml.domain.dto.export.query4.UserExportDtoQuery4;
import softuni.shopxml.domain.dto.export.query4.UserRootExportDtoQuery4;
import softuni.shopxml.domain.dto.imp.user.UserImportDto;
import softuni.shopxml.domain.dto.imp.user.UserRootImportDto;
import softuni.shopxml.domain.entities.Product;
import softuni.shopxml.domain.entities.User;
import softuni.shopxml.domain.repo.UserRepository;
import softuni.shopxml.services.ProductService;
import softuni.shopxml.services.UserService;
import softuni.shopxml.util.validator.ValidatorUtil;
import softuni.shopxml.util.xmlparser.XmlParser;

import javax.xml.bind.JAXBException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final static String USERS_IMPORT_PATH = "src/main/resources/xml-import/users.xml";
    private final static String USERS_EXPORT_PATH_QUERY2 = "src/main/resources/xml-export/query2.xml";
    private final static String USERS_EXPORT_PATH_QUERY4 = "src/main/resources/xml-export/query4.xml";

    private final UserRepository userRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final StringBuilder sb;
    private final ValidatorUtil validatorUtil;
    private final ProductService productService;

    public UserServiceImpl(UserRepository userRepository,
                           XmlParser xmlParser,
                           ModelMapper modelMapper,
                           StringBuilder sb,
                           ValidatorUtil validatorUtil,
                           ProductService productService) {
        this.userRepository = userRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.sb = sb;
        this.validatorUtil = validatorUtil;
        this.productService = productService;
    }

    @Override
    public String seedUsers() throws JAXBException {
        this.sb.setLength(0);
        UserRootImportDto userRootImportDto = xmlParser.parseXml(UserRootImportDto.class, USERS_IMPORT_PATH);

        for (UserImportDto userDt : userRootImportDto.getUsers()) {


            if (this.validatorUtil.isValid(userDt)) {
                User user = this.modelMapper.map(userDt, User.class);
                this.userRepository.saveAndFlush(user);
                sb.append(String.format("Successfully added User --> First name: %s Last Name: %s  Age: %d",
                        userDt.getFirstName(), userDt.getFirstName(), userDt.getAge()))
                        .append(System.lineSeparator());
            } else {
                this.validatorUtil.violations(userDt).forEach(err ->
                        this.sb.append(err.getMessage()).append(" ")
                                .append(String.format("For input --> First name: %s Last Name: %s  Age: %d",
                                        userDt.getFirstName(), userDt.getFirstName(), userDt.getAge()))
                                .append(System.lineSeparator()));
            }
        }

        for (User user : this.userRepository.findAll()) {
            user.setFriends(getRandomFriends());
            this.userRepository.saveAndFlush(user);
        }
        return this.sb.toString().trim();
    }

    @Override
    public String executeQuery2() throws JAXBException {
        UserRootExportDtoQuery2 root = new UserRootExportDtoQuery2();
        List<UserExportDtoQuery> dtos = new ArrayList<>();


        List<User> usersWithSales = this.userRepository.findAllUserWithProductSale();
        for (User userEntity : usersWithSales) {
            Set<Product> originalList = userEntity.getSoldProducts();

            Set<Product> productsWithBuyers = userEntity.getSoldProducts().stream()
                    .filter(this.productService::hasBuyer).collect(Collectors.toSet());

            userEntity.setSoldProducts(productsWithBuyers);

            UserExportDtoQuery dto =
                    this.modelMapper.map(userEntity, UserExportDtoQuery.class);
            dtos.add(dto);

            userEntity.setSoldProducts(originalList);
        }

        root.setUsers(dtos);
        this.xmlParser.exportToXml(root, UserRootExportDtoQuery2.class, USERS_EXPORT_PATH_QUERY2);
        return "query 2 executed";
    }

    @Override
    public String executeQuery4() throws JAXBException {
        UserRootExportDtoQuery4 root = new UserRootExportDtoQuery4();
        List<UserExportDtoQuery4> dtos = new ArrayList<>();


        List<User> entities =
                this.userRepository.findAllUsersOrderByCountProducts();
        for (User entity : entities) {
            UserExportDtoQuery4 dto = this.modelMapper.map(entity, UserExportDtoQuery4.class);
            ProductRootExportDtoQuery4 soldProducts = dto.getSoldProducts();
            soldProducts.setCount(entity.getSoldProducts().size());
            dtos.add(dto);
        }
        root.setUsers(dtos);
        this.xmlParser.exportToXml(root, UserRootExportDtoQuery4.class,USERS_EXPORT_PATH_QUERY4);
        return "Query 4 executed";
    }

    private Set<User> getRandomFriends() {
        List<User> products = this.userRepository.findAll();
        int seedAndLimitFr = 3;
        Collections.shuffle(products, new Random(seedAndLimitFr));
        return new HashSet<>(products).stream().limit(seedAndLimitFr).collect(Collectors.toSet());
    }


}
