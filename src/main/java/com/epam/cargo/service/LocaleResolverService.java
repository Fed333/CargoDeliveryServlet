package com.epam.cargo.service;

import org.fed333.servletboot.annotation.Singleton;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * Class for resolving {@link Locale} object from {@link HttpServletRequest}.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@Singleton(type = Singleton.Type.LAZY)
public class LocaleResolverService {

    private static final String DEFAULT_LOCALE_LANGUAGE = "en";
    private static final String LANG_ATTRIBUTE = "lang";

    /**
     * Gets lang parameter by {@link #LANG_ATTRIBUTE} value, with further setting to the current session.<br>
     * @param req source of request data an {@link HttpServletRequest}
     * @since 1.0
     * */
    public void interceptLocale(HttpServletRequest req){

        HttpSession session = req.getSession();
        String lang = Optional.ofNullable(req.getParameter(LANG_ATTRIBUTE)).orElse((String)session.getAttribute(LANG_ATTRIBUTE));
        req.getSession().setAttribute(LANG_ATTRIBUTE, Optional.ofNullable(lang).orElse(DEFAULT_LOCALE_LANGUAGE));

    }

    /**
     * Resolves {@link Locale} object from web {@link HttpSession}.
     * @param session source with web data
     * @return {@link Locale} object of current language if it's present, otherwise returns {@link #DEFAULT_LOCALE_LANGUAGE}
     * @since 1.0
     * */
    public Locale resolveLocale(HttpSession session){
        String lang = (String)session.getAttribute(LANG_ATTRIBUTE);
        if (Objects.isNull(lang) || lang.isBlank()){
            lang = DEFAULT_LOCALE_LANGUAGE;
        }
        return new Locale(lang);
    }

}
