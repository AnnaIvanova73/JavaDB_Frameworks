//package softuni.springintrousers.annotations;
//import javax.validation.ConstraintValidatorContext;
//
//import static softuni.springintrousers.constants.TextConstants.ERROR_PASSWORD_ANNOTATION;
//
//public final class AnnotationsUtil {
//
//    private AnnotationsUtil() {
//    }
//
//    public static void setErrorMessage(final ConstraintValidatorContext context) {
//        context.disableDefaultConstraintViolation();
//        context.buildConstraintViolationWithTemplate(ERROR_PASSWORD_ANNOTATION).addConstraintViolation();
//    }
//}
