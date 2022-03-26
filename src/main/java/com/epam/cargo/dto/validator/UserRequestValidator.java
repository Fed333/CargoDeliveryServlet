package com.epam.cargo.dto.validator;

import com.epam.cargo.dao.repo.UserRepo;
import com.epam.cargo.dto.UserRequest;
import com.epam.cargo.exception.*;

import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.epam.cargo.exception.ModelErrorAttribute.*;
import static com.epam.cargo.exception.WrongInput.*;

/**
 * Class for validation {@link UserRequest} objects.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public class UserRequestValidator extends RequestValidator<UserRequest>{

    private static final String PASSWORD_VALID_REGEX = "^(?=.*[0-9])(?=.*[a-zA-Z-_])(?=\\S+$).{8,64}";
    private static final String LOGIN_VALID_REGEX = "^(?=.*[a-zA-Z-_])(?=\\S+$).{1,32}";
    private static final String NAME_VALID_REGEX = "(^[A-Z][a-z]{1,20}$)|(^[А-ЯЄЙШЩІЇҐ][а-яєйшщіїґ']{1,20})$";
    private static final String PHONE_VALID_REGEX = "^(\\+38)?0[0-9]{9}$";


    public UserRequestValidator(ResourceBundle bundle) {
        super(bundle);
    }

    @Override
    public boolean validate(UserRequest request) {
        validateLogin(request.getLogin());
        validatePasswords(request);
        validateName(request.getName());
        validateSurname(request.getSurname());
        validatePhone(request.getPhone());

        return isValid();
    }

    @Override
    public void requireValid(UserRequest request) throws WrongDataException {
        requireValidLogin(request.getLogin());
        requireValidPassword(request.getPassword());
        requireValidPassword(request.getDuplicatePassword());
        requirePasswordDuplicationMatch(request);
        requireValidName(request);
        requireValidSurname(request);
        requireValidPhone(request);
    }

    private boolean validatePhone(String phone) {
        return validationChain(
                () -> validateNotBlank(phone, PHONE.getAttr(), REQUIRED),
                () -> validateRegex(phone, PHONE_VALID_REGEX, PHONE.getAttr(), INCORRECT_PHONE)
        );
    }

    private boolean validateSurname(String surname) {
        return validationChain(
                () -> validateNotBlank(surname, SURNAME.getAttr(), REQUIRED),
                () -> validateRegex(surname, NAME_VALID_REGEX, SURNAME.getAttr(), INCORRECT_SURNAME)
        );
    }

    private boolean validateName(String name) {
        return validationChain(
                () -> validateNotBlank(name, NAME.getAttr(), REQUIRED),
                () -> validateRegex(name, NAME_VALID_REGEX, NAME.getAttr(), INCORRECT_NAME)
        );
    }

    private boolean validatePasswords(UserRequest request) {
        String password = request.getPassword();
        String duplicatePassword = request.getDuplicatePassword();
        return validationChain(
                () -> validateNotBlank(password, PASSWORD.getAttr(), REQUIRED),
                () -> validateNotBlank(duplicatePassword, DUPLICATE_PASSWORD.getAttr(), REQUIRED),
                () -> validatePassword(password, PASSWORD.getAttr(), NO_VALID_PASSWORD),
                () -> validatePassword(duplicatePassword, DUPLICATE_PASSWORD.getAttr(), NO_VALID_PASSWORD),
                () -> validatePasswordsMatching(password, duplicatePassword)
        );
    }

    private boolean validatePasswordsMatching(String password, String duplicatePassword){
        if (!password.equals(duplicatePassword)){
            addError(DUPLICATE_PASSWORD.getAttr(), CONFIRMATION_PASSWORD_FAILED);
            return false;
        }
        return true;
    }

    private boolean validatePassword(String password, String attribute, String keyError) {
        if (!isValidPassword(password)){
            addError(attribute, keyError);
            return false;
        }
        return true;
    }

    private boolean validateLogin(String login){
        return validationChain(
                () -> validateNotBlank(login, ModelErrorAttribute.LOGIN.getAttr(), REQUIRED),
                () -> validLogin(login)
        );
    }

    private boolean validLogin(String login) {
        if (!isValidLogin(login)){
            addError(ModelErrorAttribute.LOGIN.getAttr(), NO_VALID_LOGIN);
            return false;
        }
        return true;
    }

    private void requirePasswordDuplicationMatch(UserRequest userRequest) throws WrongDataException {
        if (!userRequest.getPassword().equals(userRequest.getDuplicatePassword())){
            throw new WrongDataAttributeException(DUPLICATE_PASSWORD.getAttr(), getBundle(), CONFIRMATION_PASSWORD_FAILED);
        }
    }

    private void requireValidPassword(String password) throws NoValidPasswordException {
        if (!isValidPassword(password)){
            throw new NoValidPasswordException(getBundle(), password);
        }
    }

    private boolean isValidPassword(String password) {
        return isValidRegexp(password, PASSWORD_VALID_REGEX);
    }

    private boolean isValidLogin(String login){
        return isValidRegexp(login, LOGIN_VALID_REGEX);
    }

    private void requireCorrectLogin(String login) throws WrongDataException{
        if (!isValidLogin(login)){
            throw new NoValidLoginException(getBundle(), login);
        }
    }

    private void requireValidLogin(String login) throws WrongDataException{
        requireNotBlank(login, LOGIN.getAttr(), REQUIRED);
        requireCorrectLogin(login);
    }

    private String requireValidAttribute(String value, String regexp, ModelErrorAttribute attribute, String errorMessage) throws WrongDataAttributeException {
        if (!isValidRegexp(value, regexp)){
            throw new WrongDataAttributeException(attribute.getAttr(), getBundle(), errorMessage);
        }
        return value;
    }

    private String requireValidName(UserRequest userRequest) throws WrongDataAttributeException {
        requireNotBlank(userRequest.getName(), NAME.getAttr(), REQUIRED);
        return requireValidAttribute(userRequest.getName(), NAME_VALID_REGEX, ModelErrorAttribute.NAME, WrongInput.INCORRECT_NAME);
    }

    private String requireValidSurname(UserRequest userRequest) throws WrongDataAttributeException {
        requireNotBlank(userRequest.getSurname(), SURNAME.getAttr(), REQUIRED);
        return requireValidAttribute(userRequest.getSurname(), NAME_VALID_REGEX, ModelErrorAttribute.SURNAME, WrongInput.INCORRECT_SURNAME);
    }

    private String requireValidPhone(UserRequest userRequest) throws WrongDataAttributeException {
        requireNotBlank(userRequest.getPhone(), PHONE.getAttr(), REQUIRED);
        return requireValidAttribute(userRequest.getPhone(), PHONE_VALID_REGEX, ModelErrorAttribute.PHONE, WrongInput.INCORRECT_PHONE);
    }
}
