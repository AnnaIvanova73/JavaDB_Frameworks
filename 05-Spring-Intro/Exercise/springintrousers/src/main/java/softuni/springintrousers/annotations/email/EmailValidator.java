package softuni.springintrousers.annotations.email;

import org.springframework.stereotype.Component;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;


@Component
public class EmailValidator implements ConstraintValidator<Email, CharSequence> {

    private static final Pattern EMAIL_PATTERN = Pattern
            .compile("^([a-zA-z]){1,}([\\.\\_\\-a-zA-Z_0-9])*@([\\.\\_\\-a-zA-Z_0-9])*(\\.[a-z]{2,})$");

    private int minLength;
    private int maxLength;
    private boolean isEmailOkey;


    @Override
    public void initialize(Email email) {
        this.minLength = email.minLength();
        this.maxLength = email.maxLength();
        this.isEmailOkey = email.isValid();
    }

    @Override
    public boolean isValid(final CharSequence sequence, ConstraintValidatorContext context) {

        if (sequence == null) {
            return false;
        }

        String email = sequence.toString();

        if (email.length() > this.maxLength || email.length() < minLength) {
            return false;
        }

        if (!EMAIL_PATTERN.matcher(email).find() || this.isEmailOkey) {
            return false;
        }
        return true;
    }
    //"^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$"
}
