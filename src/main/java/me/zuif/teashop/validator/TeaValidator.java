package me.zuif.teashop.validator;

import me.zuif.teashop.model.tea.Tea;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class TeaValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Tea.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Tea tea = (Tea) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.not_empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "error.not_empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "error.not_empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "count", "error.not_empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "manufacturer", "error.not_empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "error.not_empty");
        // Name must have from 2 characters to 64
        if (tea.getName().length() <= 1) {
            errors.rejectValue("name", "tea.error.name.less_2");
        }
        if (tea.getName().length() > 64) {
            errors.rejectValue("name", "tea.error.name.over_64");
        }
    }
}
