package com.epam.cargo.dto.validator;

import com.epam.cargo.dto.AddressRequest;
import com.epam.cargo.exception.ModelErrorAttribute;
import com.epam.cargo.exception.WrongDataAttributeException;
import com.epam.cargo.exception.WrongDataException;
import com.epam.cargo.exception.WrongInput;

import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public abstract class AddressRequestValidator extends RequestValidator<AddressRequest>{


    public AddressRequestValidator(ResourceBundle bundle) {
        super(bundle);
    }

    public abstract String cityModelAttribute();

    public abstract String streetModelAttribute();

    public abstract String houseNumberModelAttribute();

    @Override
    public void requireValid(AddressRequest request) throws WrongDataException {
        requireNotNull(request.getCityId(), cityModelAttribute(), WrongInput.REQUIRED);
        requireNotBlank(request.getStreetName(), streetModelAttribute(), WrongInput.REQUIRED);
        requireNotBlank(request.getHouseNumber(), houseNumberModelAttribute(), WrongInput.REQUIRED);
    }


}
