package com.epam.cargo.service;

import com.epam.cargo.dao.repo.UserRepo;
import com.epam.cargo.dto.AddressRequest;
import com.epam.cargo.dto.UserRequest;
import com.epam.cargo.entity.Address;
import com.epam.cargo.entity.City;
import com.epam.cargo.entity.Role;
import com.epam.cargo.entity.User;
import com.epam.cargo.exception.*;
import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.infrastructure.annotation.PropertyValue;
import com.epam.cargo.infrastructure.annotation.Singleton;
import com.epam.cargo.infrastructure.security.encoding.password.PasswordEncoder;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;

import static com.epam.cargo.exception.ModelErrorAttribute.DUPLICATE_PASSWORD;
import static com.epam.cargo.exception.WrongInput.CONFIRMATION_PASSWORD_FAILED;

/**
 * Service class for managing User objects.<br>
 * @author Roman Kovalchuk
 * @see User
 * @version 1.0
 * */
@Singleton
public class UserService {

    private static final Logger logger = Logger.getLogger(UserService.class);

    private static final String PASSWORD_VALID_REGEX = "^(?=.*[0-9])(?=.*[a-zA-Z-_])(?=\\S+$).{8,64}";

    private static final String LOGIN_VALID_REGEX = "^(?=.*[a-zA-Z-_])(?=\\S+$).{1,32}";

    private static final String NAME_VALID_REGEX = "(^[A-Z][a-z]{1,20}$)|(^[А-ЯІЇҐ][а-яіїґ]{1,20})$";

    private static final String PHONE_VALID_REGEX = "^(\\+38)?0[0-9]{9}$";

    //TODO take out bonus declaration in properties file
    private BigDecimal bonus = BigDecimal.valueOf(2000);

    @Inject
    private UserRepo userRepo;

    @Inject
    private CityService cityService;

    @Inject
    private AddressService addressService;

    @Inject
    private PasswordEncoder passwordEncoder;

    @PropertyValue(property = "messages")
    private String messages;

    public User findUserById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    public User findUserByLogin(String login) {
        return userRepo.findByLogin(login);
    }

    /**
     * Creates {@link User} object from {@link UserRequest} with further registration.
     * @param userRequest data source of User
     * @param locale current locale
     * @return registered {@link User} object
     * @throws WrongDataException if source of data contains errors, or mistakes
     * @since 1.0
     * */
    public User registerUser(UserRequest userRequest, Locale locale) throws WrongDataException {
        UserInitializer initializer = new UserInitializer(new UserValidator(locale));

        User user = initializer.initialize(userRequest);
        this.addUser(user);

        return user;
    }

    /**
     * Add user to database
     * @param user given User
     * @throws IllegalArgumentException if user has inappropriate fields or null mandatory ones
     * @return true if new User has been just added, or false if User is null or already exists
     * @since 1.0
     * */
    public boolean addUser(User user) throws NoExistingCityException {
        if (Objects.isNull(user)){
            logger.warn("Attempt to add null User!");
            return false;
        }

        requireValidUser(user);

        User foundUser;
        if (!Objects.isNull(foundUser = userRepo.findByLogin(user.getLogin()))){
            user.setId(foundUser.getId());
            return false;
        }
        user.setRoles(Collections.singleton(Role.USER));

        Address address = user.getAddress();
        if (!Objects.isNull(address)) {
            addressService.addAddress(address);
        }
        user.setCash(bonus);
        userRepo.save(user);
        logger.info(String.format("User: [id=%d login=%s] has been registered successfully.", user.getId(), user.getLogin()));
        return true;
    }


    public void deleteUser(User user){
        userRepo.delete(user);
    }

    /**
     * Finds User by login and password columns.<br>
     * Takes hash of password.
     * @param login User's login
     * @param password hashed with PasswordEncoder User's password
     * @return Optional with found User, or Optional.empty() if user wasn't found
     * @since 1.0
     * */
    public Optional<User> findUserByLoginAndPassword(String login, String password) {
        return userRepo.findByLoginAndPassword(login, password);
    }

    private void requireValidUser(User user) {
        Optional.ofNullable(user.getLogin()).orElseThrow(()->new IllegalArgumentException("User login cannot be null!"));
    }

    /**
     * Inner class for initialization {@link User} object
     * */
    private class UserInitializer {

        private final UserValidator validator;

        public UserInitializer(UserValidator validator) {
            this.validator = validator;
        }

        private void initializePersonalData(UserRequest userRequest, User user) throws WrongDataException{
            user.setName(validator.requireValidName(userRequest));
            user.setSurname(validator.requireValidSurname(userRequest));
            user.setEmail(user.getEmail());
            user.setPhone(validator.requireValidPhone(userRequest));

            assignAddressToUser(userRequest, user);
        }

        private void initializeCredentials(UserRequest userRequest, User user) throws WrongDataException {
            String login = userRequest.getLogin();
            validator.requireValidLogin(login);
            validator.requireUniqueLogin(login);
            user.setLogin(login);

            String password = userRequest.getPassword();
            validator.requireValidPassword(password);
            validator.requirePasswordDuplicationMatch(userRequest, password);
            user.setPassword(passwordEncoder.encode(password));
        }

        public User initialize(UserRequest userRequest) throws WrongDataException {
            User user = new User();
            initializeCredentials(userRequest, user);
            initializePersonalData(userRequest, user);
            return user;
        }
    }

    /**
     * Inner class for validation {@link UserRequest}.
     * @author Roman Kovalchuk
     * @version 1.0
     * */
    private class UserValidator {

        /**
         * Current locale.
         * */
        private final Locale locale;

        private final ResourceBundle bundle;

        public UserValidator(Locale locale) {
            this.locale = locale;
            bundle = ResourceBundle.getBundle(messages, locale);
        }

        private void requirePasswordDuplicationMatch(UserRequest userRequest, String password) throws WrongDataException {
            if (!password.equals(userRequest.getDuplicatePassword())){
                throw new WrongDataAttributeException(DUPLICATE_PASSWORD.getAttr(), bundle, CONFIRMATION_PASSWORD_FAILED);
            }
        }

        private void requireValidPassword(String password) throws NoValidPasswordException {
            if (!isValidPassword(password)){
                throw new NoValidPasswordException(bundle, password);
            }
        }

        private boolean isValidPassword(String password) {
            return isValidRegexp(password, PASSWORD_VALID_REGEX);
        }

        private boolean isValidLogin(String login){
            return isValidRegexp(login, LOGIN_VALID_REGEX);
        }

        private boolean isValidRegexp(String value, String regexp){
            return Optional.ofNullable(value).orElse("").matches(regexp);
        }

        private void requireValidLogin(String login) throws WrongDataException{
            if (!isValidLogin(login)){
                throw new NoValidLoginException(bundle, login);
            }
        }

        private void requireUniqueLogin(String login) throws OccupiedLoginException {
            if (!Objects.isNull(userRepo.findByLogin(login))){
                throw new OccupiedLoginException(bundle, login);
            }
        }

        private String requireValidAttribute(String value, String regexp, ModelErrorAttribute attribute, String errorMessage) throws WrongDataAttributeException {
            if (!isValidRegexp(value, regexp)){
                throw new WrongDataAttributeException(attribute.getAttr(), bundle, errorMessage);
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

    private boolean assignAddressToUser(UserRequest userRequest, User user) throws NoExistingCityException {
        AddressRequest addressRequest = userRequest.getAddress();
        if (!Objects.isNull(addressRequest)) {

            City city = cityService.findCityById(addressRequest.getCityId());
            if (!Objects.isNull(city)){
                Address address = new Address();
                address.setCity(city);
                address.setStreet(addressRequest.getStreetName());
                address.setHouseNumber(addressRequest.getHouseNumber());

                addressService.addAddress(address);
                user.setAddress(address);
                return true;
            }
        }
        return false;
    }

}
