package com.epam.cargo.exception;

import com.epam.cargo.entity.City;

import java.util.ResourceBundle;

import static com.epam.cargo.exception.ModelErrorAttribute.CITY_DIRECTION;
import static com.epam.cargo.exception.WrongInput.NO_EXISTING_DIRECTION;

public class NoExistingDirectionException extends WrongDataException{

    public NoExistingDirectionException(City from, City to, ResourceBundle bundle) {
        super(buildErrorMessage(from, to, bundle));
    }

    private static String buildErrorMessage(City from, City to, ResourceBundle bundle){
        return String.format(bundle.getString(NO_EXISTING_DIRECTION), from.getName(), to.getName());
    }

    @Override
    public String getModelAttribute() {
        return CITY_DIRECTION.getAttr();
    }
}
