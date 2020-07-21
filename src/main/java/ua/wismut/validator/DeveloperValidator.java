package ua.wismut.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.wismut.model.Developer;

@Component
public class DeveloperValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Developer.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                "firstName",
                "First name can't be null or empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                "lastName",
                "Last name can't be null or empty");
    }
}
