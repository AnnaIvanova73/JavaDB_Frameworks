package softuni.jsonprocessing.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.jsonprocessing.util.ValidatorUtil;
import softuni.jsonprocessing.util.ValidatorUtilImpl;

import javax.validation.Validation;
import javax.validation.Validator;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public BufferedReader reader(){
        return new BufferedReader(new InputStreamReader(System.in));
    }

    @Bean
    public StringBuilder builder (){
        return new StringBuilder();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    // for jsons
    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, (
                        JsonDeserializer<LocalDateTime>) (json, typeOfT, context) -> LocalDateTime
                        .parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
                .create();
    }

    @Bean
    public Validator validator(){
        return Validation.buildDefaultValidatorFactory().getValidator();
    }
    @Bean
    public ValidatorUtil validatorUtil(){
        return new ValidatorUtilImpl(validator());
    }
}
