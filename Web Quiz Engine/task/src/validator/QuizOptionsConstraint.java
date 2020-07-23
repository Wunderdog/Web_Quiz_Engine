package validator;

import engine.Option;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.List;

@Documented
@Constraint(validatedBy = OptionValidator.class)
@Target({ElementType.FIELD,  ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface QuizOptionsConstraint {
    String message() default "There must be at least 2 quiz options";
    Class<?>[] groups() default  {};
    Class<? extends Payload>[] payload() default {};
}
