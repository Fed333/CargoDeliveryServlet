package com.epam.cargo.dto.validator;


import com.epam.cargo.dto.DeliveryReceiptRequest;
import com.epam.cargo.exception.WrongDataException;
import org.jetbrains.annotations.NotNull;

import java.util.ResourceBundle;

import static com.epam.cargo.exception.ModelErrorAttribute.RECEIPT;
import static com.epam.cargo.exception.WrongInput.NO_POSITIVE_NUMBER;
import static com.epam.cargo.exception.WrongInput.REQUIRED;

/**
 * Class for validation {@link DeliveryReceiptRequest} objects.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public class DeliveryReceiptRequestValidator extends RequestValidator<DeliveryReceiptRequest> {

    public DeliveryReceiptRequestValidator(@NotNull ResourceBundle bundle) {
        super(bundle);
    }

    @Override
    public boolean validate(DeliveryReceiptRequest request) {
        return validationChain(
                () -> validateNotNull(request, RECEIPT.getAttr(), REQUIRED),
                () -> validateNotNull(request.getPrice(), RECEIPT.getAttr(), REQUIRED),
                () -> validatePositive(request.getPrice(), RECEIPT.getAttr(), NO_POSITIVE_NUMBER)
        );
    }

    @Override
    public void requireValid(DeliveryReceiptRequest request) throws WrongDataException {
        requireNotNull(request, RECEIPT.getAttr(), REQUIRED);
        requireNotNull(request.getPrice(), RECEIPT.getAttr(), REQUIRED);
        requirePositive(request.getPrice(), RECEIPT.getAttr(), NO_POSITIVE_NUMBER);
    }
}
