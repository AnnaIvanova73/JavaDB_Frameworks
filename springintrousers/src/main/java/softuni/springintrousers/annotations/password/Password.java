// info --> //https://docs.oracle.com/javaee/7/api/javax/validation/Constraint.html

package softuni.springintrousers.annotations.password;


import org.springframework.stereotype.Component;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static softuni.springintrousers.constants.TextConstants.ERROR_PASSWORD_ANNOTATION;

@Component
@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({METHOD, FIELD,CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
public @interface Password {


    int minLength();//default 6

    int maxLength();

    boolean containsDigit(); //default false

    boolean containsLowerCase();

    boolean containsUpperCase();

    boolean containsSpecialSymbols();

    boolean containsWhiteSpaces() default false;



    String message() default ERROR_PASSWORD_ANNOTATION; //which should default to an error
    // message key made of the fully-qualified class name of the constraint followed by .message.
    // For example "{com.acme.constraints.NotSafe.message}"
    Class<?>[] groups() default {}; //for user to customize the targeted groups
    Class<? extends Payload>[] payload() default {}; //for extensibility purposes
}

