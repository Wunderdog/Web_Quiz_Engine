package validator;

import engine.Option;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

class OptionValidator
        implements ConstraintValidator<QuizOptionsConstraint, List<Option>> {

//    public OptionValidator() {}

    @Override
    public void initialize(QuizOptionsConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<Option> value, ConstraintValidatorContext context) {
        if ( value != null) {
            return value.size() >= 2;
        }
        return false;
    }
}

