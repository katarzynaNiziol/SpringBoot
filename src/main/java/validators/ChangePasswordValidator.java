package validators;

import constants.AppdemoConstants;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import user.User;
import utilities.AppdemoUtils;
import javax.validation.Validator;

public class ChangePasswordValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return User.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        @SuppressWarnings("unused")
        User u = (User) obj;
        ValidationUtils.rejectIfEmpty(errors, "newPassword", "error.userPassword.empty");
    }

    public void checkPasswords(String newPass, Errors errors) {

        if (!newPass.equals(null)) {
            boolean isMatch = AppdemoUtils.checkEmailOrPassword(AppdemoConstants.passwordPattern, newPass);
            if (!isMatch) {
                errors.rejectValue("newPassword", "error.userPasswordIsNotMatch");
            }
        }
    }
}

