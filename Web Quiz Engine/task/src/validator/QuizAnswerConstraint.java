package validator;

import engine.Answer;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AnswerValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface QuizAnswerConstraint {
    String message() default "Answers must be within options indices and non-duplicates, or not exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        QuizOptionsConstraint[] value();
    }
}

class AnswerValidator implements ConstraintValidator<QuizAnswerConstraint, Object> {

    private String[] options;
    private Answer answer;

    @Override
    public void initialize(QuizAnswerConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        try {
            options = (String[]) new BeanWrapperImpl(value).getPropertyValue("options");
            answer = (Answer) new BeanWrapperImpl(value).getPropertyValue("answer");
            answer.getAnswer().stream().forEach(System.out::println);

            boolean result = answer == null ? true : answer.getAnswer().stream()
                                .allMatch(ans -> ans >= 0 && ans < options.length);
            return result;
        } catch(NumberFormatException nfe) {
            return false;
        }
    }
}