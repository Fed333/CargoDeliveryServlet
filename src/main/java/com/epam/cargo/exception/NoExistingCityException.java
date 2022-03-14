package com.epam.cargo.exception;

import com.epam.cargo.entity.City;

import java.util.ResourceBundle;

import static com.epam.cargo.exception.ModelErrorAttribute.ABSENT_CITY;
import static com.epam.cargo.exception.WrongInput.NO_EXISTING_CITY;

public class NoExistingCityException extends WrongDataException{

    public NoExistingCityException(){
        super("Your city is absent in our data base!");
    }

    public NoExistingCityException(City city, ResourceBundle bundle) {
        super(buildErrorMessage(city, bundle));
    }

    private static String buildErrorMessage(City city, ResourceBundle bundle) {
        return String.format(bundle.getString(NO_EXISTING_CITY), city.getName());
    }

    @Override
    public String getModelAttribute() {
        return ABSENT_CITY.getAttr();
    }

}
