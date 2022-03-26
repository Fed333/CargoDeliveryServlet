package com.epam.cargo.dto.validator;

import com.epam.cargo.dto.DeliveredBaggageRequest;
import com.epam.cargo.exception.ModelErrorAttribute;
import com.epam.cargo.exception.WrongDataAttributeException;
import com.epam.cargo.exception.WrongInput;

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
    public boolean validate(DeliveredBaggageRequest request) {
        validateNotNull(request.getType(), ModelErrorAttribute.BAGGAGE_TYPE.getAttr(), WrongInput.REQUIRED);
        validateWeight(request);
        validateVolume(request);

        return isValid();
    }

    @Override
    public void requireValid(DeliveredBaggageRequest request) throws WrongDataAttributeException {
        requireNotNull(request.getType(), ModelErrorAttribute.BAGGAGE_TYPE.getAttr(), WrongInput.REQUIRED);
        requireValidWeight(request);
        requireValidVolume(request);
    }

    private boolean validateVolume(DeliveredBaggageRequest request) {
        Double volume = request.getVolume();
        return validationChain(
                () -> validateNotNull(volume, ModelErrorAttribute.VOLUME.getAttr(), WrongInput.REQUIRED),
                () -> validatePositive(volume, ModelErrorAttribute.VOLUME.getAttr(), WrongInput.NO_POSITIVE_NUMBER)
        );
    }

    private boolean validateWeight(DeliveredBaggageRequest request) {
        Double weight = request.getWeight();
        return validationChain(
                () -> validateNotNull(weight, ModelErrorAttribute.WEIGHT.getAttr(), WrongInput.REQUIRED),
                () -> validatePositive(weight, ModelErrorAttribute.WEIGHT.getAttr(), WrongInput.NO_POSITIVE_NUMBER)
        );
    }

    private void requireValidVolume(DeliveredBaggageRequest request) throws WrongDataAttributeException {
        requireNotNull(request.getVolume(), ModelErrorAttribute.VOLUME.getAttr(), WrongInput.REQUIRED);
    }

    private void requireValidWeight(DeliveredBaggageRequest request) throws WrongDataAttributeException {
        requireNotNull(request.getWeight(), ModelErrorAttribute.WEIGHT.getAttr(), WrongInput.REQUIRED);
    }

}