package com.epam.cargo.infrastructure.web;

import java.util.Map;

import com.epam.cargo.infrastructure.annotation.Controller;
import com.epam.cargo.infrastructure.dispatcher.Command;
import javax.servlet.http.HttpServletRequest;

/**
 * Part of web transporting data layer between servlet's Command and Controller classes. <br/>
 * Contains data to transfer from Controller into HttpServletRequest.
 * @see Controller
 * @see Command
 * @see HttpServletRequest
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public interface Model {

    void addAttribute(String attribute, Object value);

    Object getAttribute(String attribute);

    Model mergeAttributes(Map<String, Object> toMerge);

    boolean containsAttribute(String attribute);

    Map<String, Object> asMap();
}
