package com.epam.cargo.dto.validator;

import com.epam.cargo.exception.WrongDataAttributeException;
import com.epam.cargo.exception.WrongDataException;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Supplier;

/**
 * Abstract class for validation content of dto objects.<br>
 * @author Roman Kovalchuk
 * @version 1.1
 * */
public abstract class RequestValidator <T>{

    private final ResourceBundle bundle;

    private final Map<String, String> errors = new HashMap<>();

    public RequestValidator(@NotNull ResourceBundle bundle) {
        this.bundle = bundle;
    }

    @NotNull
    protected ResourceBundle getBundle(){
        return bundle;
    }

    /**
     * Validates given object.<br>
     * Garbage all validation errors into {@link #errors} map.
     * @param request object to validate
     * @return true if validation passed successfully, otherwise false
     * @since 1.1
     * */
    abstract public boolean validate(T request);

    /**
     * Checks whether content of object is valid.<br>
     * @param request object to validate
     * @throws WrongDataException in case of any validation error
     * @since 1.0
     * */
    abstract public void requireValid(T request) throws WrongDataException;

    /**
     * Checks whether the  object isn't null.<br>
     * @param o {@link Object} to validate
     * @param modelAttribute name of attribute in model
     * @param keyErrorMessage name of error message key in ResourceBundle
     * @return true if parameter o is not null, otherwise false
     * */
    protected boolean validateNotNull(Object o, String modelAttribute, String keyErrorMessage){
        if (Objects.isNull(o)){
            errors.put(modelAttribute, bundle.getString(keyErrorMessage));
            return false;
        }
        return true;
    }

    /**
     * Checks whether the string is present.<br>
     * @param s {@link String} to validate
     * @param modelAttribute name of attribute in model
     * @param keyErrorMessage name of error message key in ResourceBundle
     * @return true if parameter s is not null and is not blank, otherwise false
     * */
    protected boolean validateNotBlank(String s, String modelAttribute, String keyErrorMessage){
        if (Objects.isNull(s) || s.isBlank()){
            errors.put(modelAttribute, bundle.getString(keyErrorMessage));
            return false;
        }
        return true;
    }

    protected boolean validatePositive(Number n, String modelAttribute, String keyErrorMessage){
        if (n.doubleValue() <= 0){
            errors.put(modelAttribute, keyErrorMessage);
            return false;
        }
        return true;
    }

    protected boolean validateRegex(String s, String regex, String attribute, String keyError){
        if (!s.matches(regex)){
            addError(attribute, keyError);
            return false;
        }
        return true;
    }

    /**
     * Validates until first validation fail.<br>
     * @param chainList array with functions of object validation
     * @return true if all validations were passed successfully, otherwise false
     * @since 1.1
     * */
    @SafeVarargs
    protected final boolean validationChain(Supplier<Boolean>... chainList){
        for (Supplier<Boolean> s : chainList) {
            if (s.get() == Boolean.FALSE){
                return false;
            }
        }
        return true;
    }

    /**
     * Adds new error to the errors.
     * @param attribute name of attribute in model
     * @param keyError name of error key in ResourceBundle
     * @since 1.0
     * */
    protected void addError(String attribute, String keyError){
        errors.put(attribute, bundle.getString(keyError));
    }

    protected void merge(RequestValidator<?> validator){
        errors.putAll(validator.errors);
    }

    /**
     * Checks whether the  object isn't null.<br>
     * @param o {@link Object} to validate
     * @param modelAttribute name of attribute in model
     * @param keyErrorMessage name of error message key in ResourceBundle
     * @throws WrongDataException if parameter o is null
     * */
    protected void requireNotNull(Object o, String modelAttribute, String keyErrorMessage) throws WrongDataAttributeException {
        if (Objects.isNull(o)){
            throw new WrongDataAttributeException(modelAttribute, bundle, keyErrorMessage);
        }
    }

    /**
     * Checks whether the string is present.<br>
     * @param s {@link String} to validate
     * @param modelAttribute name of attribute in model
     * @param keyErrorMessage name of error message key in ResourceBundle
     * @throws WrongDataAttributeException if parameter s is null or is blank
     * */
    protected void requireNotBlank(String s, String modelAttribute, String keyErrorMessage) throws WrongDataAttributeException {
        if (Objects.isNull(s) || s.isBlank()){
            throw new WrongDataAttributeException(modelAttribute, bundle, keyErrorMessage);
        }
    }

    protected boolean isValidRegexp(String value, String regexp){
        return Optional.ofNullable(value).orElse("").matches(regexp);
    }

    /**
     * Checks whether there are any errors.<br>
     * @return false if validation passed successfully, otherwise true
     * @since 1.0
     * */
    public boolean hasErrors(){
        return !errors.isEmpty();
    }

    /**
     * Checks whether there are no errors.<br>
     * @return true if validation passed successfully, otherwise false
     * @since 1.1
     * */
    protected boolean isValid(){
        return errors.isEmpty();
    }

    /**
     * Gives all existing validation errors.<br>
     * @return {@link Map} with errors
     * @since 1.0
     * */
    public Map<String, String> getErrors(){
        return errors;
    };

}