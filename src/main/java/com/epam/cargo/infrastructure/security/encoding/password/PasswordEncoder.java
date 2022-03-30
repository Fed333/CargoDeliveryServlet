package com.epam.cargo.infrastructure.security.encoding.password;

/**
 * Interface for implementing password encoders.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public interface PasswordEncoder {

    String encode(CharSequence password);

    boolean matches(CharSequence password, String hash);
}
