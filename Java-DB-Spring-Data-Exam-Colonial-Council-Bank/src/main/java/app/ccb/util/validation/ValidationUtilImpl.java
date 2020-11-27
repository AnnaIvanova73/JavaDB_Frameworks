package app.ccb.util.validation;

import javax.validation.Validator;


public class ValidationUtilImpl implements ValidationUtil {

    private final Validator validator;


    public ValidationUtilImpl(Validator validator) {
        this.validator = validator;
    }


    @Override
    public <E> boolean isValid(E entity) {
            return this.validator.validate(entity).isEmpty();
    }


}
