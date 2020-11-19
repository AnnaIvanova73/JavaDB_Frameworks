package softuni.gameshop.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.gameshop.utils.ValidatorUtil;
import softuni.gameshop.utils.ValidatorUtilImpl;

import javax.validation.Validation;
import javax.validation.Validator;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;


@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    @Bean
    public BufferedReader reader(){
        return new BufferedReader(new InputStreamReader(System.in));
    }
    @Bean
    public StringBuilder builder (){
        return new StringBuilder();
    }

    @Bean
    public Validator validator(){
        return Validation.buildDefaultValidatorFactory().getValidator();
    }
    @Bean
    public ValidatorUtil validatorUtil(){
        return new ValidatorUtilImpl(validator());
    }

    @Bean
    public DateTimeFormatter formatter(){
        return DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }
//    @Bean
//    public LoggedUsers loggedUsers (){
//        return new LoggedUsers();
//    }

}
