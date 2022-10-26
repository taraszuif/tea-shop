package me.zuif.teashop.validator;

import me.zuif.teashop.model.user.User;
import me.zuif.teashop.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        //Username, password and email can't me empty or contain whitespace
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "error.not_empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.not_empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.not_empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "error.not_empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "error.not_empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "error.not_empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "age", "error.not_empty");
        // Username must have from 3 characters to 16
        if (user.getUserName().length() < 3) {
            errors.rejectValue("username", "register.error.username.less_3");
        }
        if (user.getUserName().length() > 24) {
            errors.rejectValue("username", "register.error.username.over_24");
        }
        //Username can't be duplicated
        if (userService.findByUsername(user.getUserName()) != null) {
            errors.rejectValue("userName", "register.error.duplicated.username");
        }
        //Email can't be duplicated
        if (userService.findByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "register.error.duplicated.email");
        }
        //Password must have at least 8 characters and max 32
        if (user.getPassword().length() < 8) {
            errors.rejectValue("password", "register.error.password.less_8");
        }
        if (user.getPassword().length() > 32) {
            errors.rejectValue("password", "register.error.password.over_32");
        }
    }
}
