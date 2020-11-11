// info -->
////https://docs.oracle.com/javaee/7/api/javax/validation/ConstraintValidatorContext.html
////https://docs.oracle.com/javaee/7/api/javax/validation/ConstraintValidator.html

package softuni.springintrousers.annotations.password;

import org.springframework.stereotype.Component;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

@Component
public class PasswordValidator implements ConstraintValidator<Password, CharSequence> {
//    private static final Pattern PATTERN_COMPONENTS = Pattern.compile(
//            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\\\S+$).{6,50}$");

    private static final Pattern DIGIT = Pattern.compile("(?=.*[0-9])");
    private static final Pattern LOWER_LETTER = Pattern.compile("(?=.*[a-z])");
    private static final Pattern UPPER_LETTER = Pattern.compile("(?=.*[A-Z])");
    private static final Pattern SPECIAL_CHAR = Pattern.compile("(?=.*[@#$%^&-+=()])");
    private static final Pattern NO_WHITE_SPACES_PLEASE = Pattern.compile("\\s+");


    private int minLength;
    private int maxLength;
    private boolean hasLowerLetter;
    private boolean hasUpperLetter;
    private boolean hasSpecialChar;
    private boolean hasWhiteSpaces;
    private boolean hasDigit;

    @Override
    public void initialize(Password password) {
        //	initialize(A constraintAnnotation) Initializes the validator in preparation for isValid(Object, ConstraintValidatorContext) calls.
//	The constraint annotation for a given constraint declaration is passed.
        this.minLength = password.minLength();
        this.maxLength = password.maxLength();
        this.hasLowerLetter = password.containsLowerCase();
        this.hasUpperLetter = password.containsUpperCase();
        this.hasSpecialChar = password.containsSpecialSymbols();
        this.hasWhiteSpaces = password.containsWhiteSpaces();
        this.hasDigit= password.containsDigit();
    }

    @Override
    public boolean isValid(final CharSequence value,
                           ConstraintValidatorContext constraintValidatorContext) {
//isValid(T value, ConstraintValidatorContext context)
        if(value == null ){
            return false;
        }


        if(value.length() < this.minLength || value.length() > this.maxLength){

            return false;

        }

        String password = value.toString();

        if(NO_WHITE_SPACES_PLEASE.matcher(password).find() && this.hasWhiteSpaces){
//            AnnotationsUtil.setErrorMessage(constraintValidatorContext);
            return false;

        }
        if (!LOWER_LETTER.matcher(password).find() && this.hasLowerLetter) {
            return false;
        }

        if (!UPPER_LETTER.matcher(password).find() && this.hasUpperLetter) {
            return false;
        }

        if (!DIGIT.matcher(password).find() && this.hasDigit) {
            return false;
        }

        if (!SPECIAL_CHAR.matcher(password).find() && this.hasSpecialChar) {
            return false;
        }

        return true;
    }

}

