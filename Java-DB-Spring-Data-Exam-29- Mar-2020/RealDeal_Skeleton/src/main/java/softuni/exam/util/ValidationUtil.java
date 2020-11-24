package softuni.exam.util;


import javax.validation.ConstraintViolation;
import java.util.Set;

// ToDo Implement interface
public interface ValidationUtil {

    <E> boolean isValid (E entity);

    <E> Set<ConstraintViolation<E>> violations (E entity);
}
