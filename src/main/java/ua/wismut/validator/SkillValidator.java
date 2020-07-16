package ua.wismut.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.wismut.model.Skill;

@Component
public class SkillValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Skill.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Skill skill = (Skill) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Skill name can't be empty");
    }
}
