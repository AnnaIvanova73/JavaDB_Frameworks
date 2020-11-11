package softuni.springintrousers.annotations.email;


import org.springframework.stereotype.Component;
import softuni.springintrousers.annotations.password.Password;


import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static softuni.springintrousers.constants.TextConstants.ERROR_PASSWORD_ANNOTATION;

// Предполагам не е вярно
@Component
@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target({METHOD, FIELD, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
public @interface Email {

    int minLength();
    int maxLength();
    boolean isValid();



    String message() default ERROR_PASSWORD_ANNOTATION; //which should default to an error

    // message key made of the fully-qualified class name of the constraint followed by .message.
    // For example "{com.acme.constraints.NotSafe.message}"
    Class<?>[] groups() default {}; //for user to customize the targeted groups

    Class<? extends Payload>[] payload() default {}; //for extensibility purposes
}
