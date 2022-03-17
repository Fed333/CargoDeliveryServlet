package com.epam.cargo.infrastructure.security.encoding.password.impl;

import com.epam.cargo.infrastructure.security.encoding.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * Implementation of password encoder based on md5 encoding algorithm.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public class Md5PasswordEncoder implements PasswordEncoder {

    private static final String ALGORITHM_NAME = "MD5";

    @Override
    public String encode(CharSequence password) {
        requirePasswordPresence(password);
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM_NAME);
            byte[] bytes = digest.digest(password.toString().getBytes(StandardCharsets.UTF_8));

            StringBuilder hashBuilder = new StringBuilder();
            for (byte b : bytes) {
                hashBuilder.append(String.format("%02x", b));
            }
            return hashBuilder.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("No found algorithm!", e);
        }
    }

    @Override
    public boolean matches(CharSequence password, String hash) {
        return encode(password).equals(hash);
    }

    private void requirePasswordPresence(CharSequence password) {
        if (Objects.isNull(password) || password.toString().isBlank()){
            throw new IllegalArgumentException("Password to encode cannot be null!");
        }
    }
}
