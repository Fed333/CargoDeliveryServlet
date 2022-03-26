package com.epam.cargo.dto.validator;

import com.epam.cargo.dto.DeliveredBaggageRequest;
import com.epam.cargo.exception.ModelErrorAttribute;
import com.epam.cargo.exception.WrongDataAttributeException;
import com.epam.cargo.exception.WrongInput;

import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Class for validation {@link DeliveredBaggageRequest} objects.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public class DeliveredBaggageRequestValidator extends RequestValidator<DeliveredBaggageRequest> {


    public DeliveredBaggageRequestValidator(ResourceBundle bundle) {
       super(bundle);
    }

    @Override
    public void requireValid(DeliveredBaggageRequest request) throws WrongDataAttributeException {
        Objects.requireNonNull(request.getType(), "BaggageType cannot be null!");
        requireValidWeight(request);
        requireValidVolume(request);
    }

    private void requireValidVolume(DeliveredBaggageRequest request) throws WrongDataAttributeException {
        requireNotNull(request.getVolume(), ModelErrorAttribute.VOLUME.getAttr(), WrongInput.REQUIRED);
    }

    private void requireValidWeight(DeliveredBaggageRequest request) throws WrongDataAttributeException {
        requireNotNull(request.getWeight(), ModelErrorAttribute.WEIGHT.getAttr(), WrongInput.REQUIRED);
    }
}