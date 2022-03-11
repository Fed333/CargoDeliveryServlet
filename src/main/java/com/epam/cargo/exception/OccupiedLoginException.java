package com.epam.cargo.exception;

import java.util.ResourceBundle;

import static com.epam.cargo.exception.WrongInput.OCCUPIED_LOGIN_FORMAT;

public class OccupiedLoginException extends WrongDataException{

    public OccupiedLoginException(ResourceBundle bundle, String login) {
        super(buildErrorMessage(bundle, login));
    }

    private static String buildErrorMessage(ResourceBundle bundle, String login) {
        return String.format(bundle.getString(OCCUPIED_LOGIN_FORMAT), login);
    }

    @Override
    public String getModelAttribute() {
        return ModelErrorAttribute.LOGIN.getAttr();
    }
}
