package com.epam.cargo.exception;


import java.util.Objects;
import java.util.ResourceBundle;

import static com.epam.cargo.exception.WrongInput.NO_FILLED_PASSWORD;
import static com.epam.cargo.exception.WrongInput.NO_VALID_PASSWORD;

public class NoValidPasswordException extends WrongDataException{

    public NoValidPasswordException(ResourceBundle bundle, String password) {
        super(getErrorMessage(bundle, password));
    }

    private static String getErrorMessage(ResourceBundle bundle, String password) {
        if (Objects.isNull(password) || password.isBlank()){
            return bundle.getString(NO_FILLED_PASSWORD);
        }
        return bundle.getString(NO_VALID_PASSWORD);
    }

    @Override
    public String getModelAttribute() {
        return ModelErrorAttribute.PASSWORD.getAttr();
    }
}
