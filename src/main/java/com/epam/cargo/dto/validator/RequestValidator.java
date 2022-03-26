package com.epam.cargo.dto.validator;

import com.epam.cargo.exception.WrongDataAttributeException;
import com.epam.cargo.exception.WrongDataException;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Abstract class for validation content of dto objects.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public abstract class RequestValidator <T>{

    private final ResourceBundle bundle;

    public RequestValidator(@NotNull ResourceBundle bundle) {
        this.bundle = bundle;
    }

    @NotNull
    protected ResourceBundle getBundle(){
        return bundle;
    }

    abstract public void requireValid(T request) throws WrongDataException;

    protected void requireNotNull(Object o, String modelAttribute, String keyErrorMessage) throws WrongDataAttributeException {
        if (Objects.isNull(o)){
            throw new WrongDataAttributeException(modelAttribute, bundle, keyErrorMessage);
        }
    }

    protected void requireNotBlank(String s, String modelAttribute, String keyErrorMessage) throws WrongDataAttributeException {
        if (Objects.isNull(s) || s.isBlank()){
            throw new WrongDataAttributeException(modelAttribute, bundle, keyErrorMessage);
        }
    }

    protected boolean isValidRegexp(String value, String regexp){
        return Optional.ofNullable(value).orElse("").matches(regexp);
    }

}
