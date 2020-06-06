package validator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = OptionValidator.class)
@Target({ElementType.FIELD,  ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface QuizOptionsConstraint {
    String message() default "There must be at least 2 quiz options";
    Class<?>[] groups() default  {};
    Class<? extends Payload>[] payload() default {};
}

class OptionValidator
        implements ConstraintValidator<QuizOptionsConstraint, String[]> {

    @Override
    public void initialize(QuizOptionsConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String[] value, ConstraintValidatorContext context) {
        if ( value != null) {
            return value.length >= 2;
        }
        return false;
    }
}
