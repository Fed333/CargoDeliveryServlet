package com.epam.cargo.exception;

import java.util.Objects;
import java.util.ResourceBundle;

import static com.epam.cargo.exception.WrongInput.NO_FILLED_LOGIN;
import static com.epam.cargo.exception.WrongInput.NO_VALID_LOGIN;

public class NoValidLoginException extends WrongDataException{

    public NoValidLoginException(ResourceBundle bundle, String login) {
        super(getErrorMessage(bundle, login));
    }

    private static String getErrorMessage(ResourceBundle bundle, String login) {
        if (Objects.isNull(login) || login.isBlank()){
            return bundle.getString(NO_FILLED_LOGIN);
        }
        return bundle.getString(NO_VALID_LOGIN);
    }

    @Override
    public String getModelAttribute() {
        return ModelErrorAttribute.LOGIN.getAttr();
    }
}
