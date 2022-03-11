package com.epam.cargo.exception;

import java.util.ResourceBundle;

public class WrongDataAttributeException extends WrongDataException{

    private final String attributeName;

    public WrongDataAttributeException(String attribute, ResourceBundle bundle, String keyMessage){
        super(bundle, keyMessage);
        attributeName = attribute;
    }

    @Override
    public String getModelAttribute() {
        return attributeName;
    }
}
