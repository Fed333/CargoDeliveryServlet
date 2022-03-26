package com.epam.cargo.dto.validator;

import com.epam.cargo.dto.DeliveredBaggageRequest;
import com.epam.cargo.dto.DeliveryApplicationRequest;
import com.epam.cargo.dto.UserRequest;
import com.epam.cargo.exception.ModelErrorAttribute;
import com.epam.cargo.exception.WrongDataAttributeException;
import com.epam.cargo.exception.WrongDataException;
import com.epam.cargo.exception.WrongInput;

import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Class for validation {@link UserRequest} objects.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public class DeliveryApplicationRequestValidator extends RequestValidator<DeliveryApplicationRequest> {


    public DeliveryApplicationRequestValidator(ResourceBundle bundle) {
        super(bundle);
    }

    @Override
    public void requireValid(DeliveryApplicationRequest request) throws WrongDataException {

        requireValidSendingDate(request.getSendingDate());
        requireValidReceivingDate(request.getReceivingDate());

        new DeliveredBaggageRequestValidator(getBundle()).requireValid(request.getDeliveredBaggageRequest());
        new SenderAddressRequestValidator(getBundle()).requireValid(request.getSenderAddress());
        new ReceiverAddressRequestValidator(getBundle()).requireValid(request.getReceiverAddress());

    }

    private void requireValidDate(LocalDate date, ModelErrorAttribute attribute, String keyErrorMessage) throws WrongDataAttributeException {
        requireNotNull(date, attribute.getAttr(), keyErrorMessage);
    }

    private void requireValidSendingDate(LocalDate date) throws WrongDataAttributeException {
        requireValidDate(date, ModelErrorAttribute.SENDING, WrongInput.REQUIRED);
    }

    private void requireValidReceivingDate(LocalDate date) throws WrongDataAttributeException {
        requireValidDate(date, ModelErrorAttribute.RECEIVING, WrongInput.REQUIRED);
    }

    private static class SenderAddressRequestValidator extends AddressRequestValidator {

        public SenderAddressRequestValidator(ResourceBundle bundle) {
            super(bundle);
        }

        @Override
        public String cityModelAttribute() {
            return ModelErrorAttribute.SENDER_CITY_ID.getAttr();
        }

        @Override
        public String streetModelAttribute() {
            return ModelErrorAttribute.SENDER_STREET.getAttr();
        }

        @Override
        public String houseNumberModelAttribute() {
            return ModelErrorAttribute.SENDER_HOUSE_NUMBER.getAttr();
        }
    }

    private static class ReceiverAddressRequestValidator extends AddressRequestValidator {

        public ReceiverAddressRequestValidator(ResourceBundle bundle) {
            super(bundle);
        }

        @Override
        public String cityModelAttribute() {
            return ModelErrorAttribute.RECEIVER_CITY_ID.getAttr();
        }

        @Override
        public String streetModelAttribute() {
            return ModelErrorAttribute.RECEIVER_STREET.getAttr();
        }

        @Override
        public String houseNumberModelAttribute() {
            return ModelErrorAttribute.RECEIVER_HOUSE_NUMBER.getAttr();
        }
    }

}
