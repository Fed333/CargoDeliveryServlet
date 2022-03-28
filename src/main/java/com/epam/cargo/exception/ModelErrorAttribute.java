package com.epam.cargo.exception;

public enum ModelErrorAttribute {
    DUPLICATE_PASSWORD("duplicatePasswordErrorMessage"),
    LOGIN("loginErrorMessage"),
    PASSWORD("passwordErrorMessage"),
    CREDENTIALS("credentialsErrorMessage"),
    NAME("nameErrorMessage"),
    PHONE("phoneErrorMessage"),
    SURNAME("surnameErrorMessage"),
    SENDING("sendingDateErrorMessage"),
    RECEIVING("receivingDateErrorMessage"),
    CITY_DIRECTION("invalidCityDirectionErrorMessage"),
    PAYING("payingErrorMessage"),
    ABSENT_CITY("noExistingCityMessage"),
    LENGTH("lengthErrorMessage"),
    WIDTH("widthErrorMessage"),
    HEIGHT("heightErrorMessage"),
    WEIGHT("weightErrorMessage"),
    VOLUME("volumeErrorMessage"),
    SENDER_STREET("senderStreetErrorMessage"),
    RECEIVER_STREET("receiverStreetErrorMessage"),
    SENDER_HOUSE_NUMBER("senderHouseNumberErrorMessage"),
    RECEIVER_HOUSE_NUMBER("receiverHouseNumberErrorMessage"),
    SENDER_CITY_ID("senderCityIdErrorMessage"),
    RECEIVER_CITY_ID("receiverCityIdErrorMessage"),
    BAGGAGE_TYPE("baggageTypeErrorMessage"),
    RECEIPT("receiptErrorMessage");

    private final String attr;

    ModelErrorAttribute(String attr) {
        this.attr = attr;
    }

    public String getAttr() {
        return attr;
    }
}
