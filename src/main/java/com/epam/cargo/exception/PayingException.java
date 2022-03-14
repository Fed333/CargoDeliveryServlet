package com.epam.cargo.exception;

public class PayingException extends Exception{

    public static final String CURRENCY_UAH_KEY = "lang.UAH";

    public PayingException(String message) {
        super(message);
    }

    public String getAttribute(){
        return ModelErrorAttribute.PAYING.getAttr();
    }
}
