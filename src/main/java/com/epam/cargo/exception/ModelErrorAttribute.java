package com.epam.cargo.exception;

public enum ModelErrorAttribute {
    DUPLICATE_PASSWORD("duplicatePasswordErrorMessage"),
    LOGIN("loginErrorMessage"),
    PASSWORD("passwordErrorMessage"),
    NAME("nameErrorMessage"),
    PHONE("phoneErrorMessage"),
    SURNAME("surnameErrorMessage"),
    RECEIVING("receivingDateErrorMessage"),
    CITY_DIRECTION("invalidCityDirectionErrorMessage"),
    PAYING("payingErrorMessage"),
    ABSENT_CITY("noExistingCityMessage");

    private final String attr;

    ModelErrorAttribute(String attr) {
        this.attr = attr;
    }

    public String getAttr() {
        return attr;
    }
}
