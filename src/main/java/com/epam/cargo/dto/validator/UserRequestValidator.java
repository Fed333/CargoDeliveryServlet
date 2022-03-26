package com.epam.cargo.dto.validator;

import com.epam.cargo.dao.repo.UserRepo;
import com.epam.cargo.dto.UserRequest;
import com.epam.cargo.exception.*;

import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.epam.cargo.exception.ModelErrorAttribute.DUPLICATE_PASSWORD;
import static com.epam.cargo.exception.WrongInput.CONFIRMATION_PASSWORD_FAILED;

/**
 * Class for validation {@link UserRequest} objects.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public class UserRequestValidator extends RequestValidator<UserRequest>{

    private static final String PASSWORD_VALID_REGEX = "^(?=.*[0-9])(?=.*[a-zA-Z-_])(?=\\S+$).{8,64}";
    private static final String LOGIN_VALID_REGEX = "^(?=.*[a-zA-Z-_])(?=\\S+$).{1,32}";
    private static final String NAME_VALID_REGEX = "(^[A-Z][a-z]{1,20}$)|(^[А-ЯІЇҐ][а-яіїґ]{1,20})$";
    private static final String PHONE_VALID_REGEX = "^(\\+38)?0[0-9]{9}$";

    private final UserRepo userRepo;

    public UserRequestValidator(ResourceBundle bundle, UserRepo repo) {
        super(bundle);
        this.userRepo = repo;
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
        requireCorrectLogin(login);
        requireUniqueLogin(login);
    }

    private void requireUniqueLogin(String login) throws OccupiedLoginException {

        if (!Objects.isNull(userRepo.findByLogin(login))){
            throw new OccupiedLoginException(getBundle(), login);
        }
    }

    private String requireValidAttribute(String value, String regexp, ModelErrorAttribute attribute, String errorMessage) throws WrongDataAttributeException {
        if (!isValidRegexp(value, regexp)){
            throw new WrongDataAttributeException(attribute.getAttr(), getBundle(), errorMessage);
        }
        return value;
    }

    private String requireValidName(UserRequest userRequest) throws WrongDataAttributeException {
        return requireValidAttribute(userRequest.getName(), NAME_VALID_REGEX, ModelErrorAttribute.NAME, WrongInput.INCORRECT_NAME);
    }

    private String requireValidSurname(UserRequest userRequest) throws WrongDataAttributeException {
        return requireValidAttribute(userRequest.getSurname(), NAME_VALID_REGEX, ModelErrorAttribute.SURNAME, WrongInput.INCORRECT_SURNAME);
    }

    private String requireValidPhone(UserRequest userRequest) throws WrongDataAttributeException {
        return requireValidAttribute(userRequest.getPhone(), PHONE_VALID_REGEX, ModelErrorAttribute.PHONE, WrongInput.INCORRECT_PHONE);
    }
}
