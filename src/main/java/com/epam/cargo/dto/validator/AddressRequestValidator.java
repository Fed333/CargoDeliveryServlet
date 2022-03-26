package com.epam.cargo.dto.validator;

import com.epam.cargo.dto.AddressRequest;
import com.epam.cargo.exception.WrongDataException;
import com.epam.cargo.exception.WrongInput;

import java.util.ResourceBundle;

/**
 * Class for validation {@link AddressRequest} objects.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public abstract class AddressRequestValidator extends RequestValidator<AddressRequest>{

    public AddressRequestValidator(ResourceBundle bundle) {
        super(bundle);
    }

    public abstract String cityModelAttribute();

    public abstract String streetModelAttribute();

    public abstract String houseNumberModelAttribute();

    @Override
    public boolean validate(AddressRequest request) {
        validateNotNull(request.getCityId(), cityModelAttribute(), WrongInput.REQUIRED);
        validateNotBlank(request.getStreetName(), streetModelAttribute(), WrongInput.REQUIRED);
        validateNotBlank(request.getHouseNumber(), houseNumberModelAttribute(), WrongInput.REQUIRED);
        return isValid();
    }

    @Override
    public void requireValid(AddressRequest request) throws WrongDataException {
        requireNotNull(request.getCityId(), cityModelAttribute(), WrongInput.REQUIRED);
        requireNotBlank(request.getStreetName(), streetModelAttribute(), WrongInput.REQUIRED);
        requireNotBlank(request.getHouseNumber(), houseNumberModelAttribute(), WrongInput.REQUIRED);
    }


}
