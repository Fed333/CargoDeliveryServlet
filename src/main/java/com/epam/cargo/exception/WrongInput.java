package com.epam.cargo.exception;

public interface WrongInput {

    String NO_FILLED_LOGIN = "wrong-input.required.login";
    String NO_VALID_LOGIN = "wrong-input.incorrect.login";
    String OCCUPIED_LOGIN_FORMAT = "wrong-input.taken-login.format";
    String NO_FILLED_PASSWORD = "wrong-input.required.password";
    String NO_VALID_PASSWORD = "wrong-input.incorrect.password";
    String INCORRECT_NAME = "wrong-input.incorrect.name";
    String INCORRECT_SURNAME = "wrong-input.incorrect.surname";
    String SMALL_PASSWORD = "wrong-input.small-password";
    String CONFIRMATION_PASSWORD_FAILED = "wrong-input.confirmation-failed.duplicate-password";
    String MISSING_DUPLICATE_PASSWORD = "wrong-input.required.duplicate-password";
    String NO_EXISTING_CITY = "wrong-input.missing.city.format";
    String NO_EXISTING_DIRECTION = "wrong-input.missing.direction.format";
    String NO_POSITIVE_NUMBER = "wrong-input.no-positive-number";
    String REQUIRED = "lang.required";
    String INCORRECT_NUMBER_FORMAT = "wrong-input.incorrect.number-format";
    String USE_DOT_INSTEAD_OF_COMMA = "wrong-input.incorrect.use-fraction-point";
    String INVALID_DIRECTION_SAME_CITIES = "wrong-input.invalid.city-direction.same-cities";
    String INVALID_CITY_DIRECTION = "wrong-input.invalid.city-direction";
    String UNKNOWN_ERROR = "unknown-error";
    String REQUIRED_BEING_AFTER_SENDING = "wrong-input.date.receiving.required-being-after-sending";

    String INCORRECT_PHONE = "wrong-input.incorrect.phone";
    String UNFILLED_NAME = "wrong.input.unfilled.name";
    String UNFILLED_SURNAME = "wrong.input.unfilled.surname";
    String UNFILLED_PHONE = "wrong.input.unfilled.phone";
}
