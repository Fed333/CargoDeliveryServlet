package com.epam.cargo.service;

import com.epam.cargo.dto.AuthorizedDataRequest;
import com.epam.cargo.entity.User;
import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.infrastructure.annotation.Singleton;
import com.epam.cargo.infrastructure.security.encoding.password.PasswordEncoder;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Class to manage authorization operations.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@Singleton
public class AuthorizationService {

    public static final String AUTHORIZED_USER = "authorizedUser";

    @Inject
    private UserService userService;

    @Inject
    private PasswordEncoder encoder;

    /**
     * Sings in the user account. Checks whether under these credentials exists authorized User.<br>
     * If is authorized, adds authorized User to the session.<br>
     * @param request dto request with credentials data
     * @param session current web session
     * @return true if User has been authorized, otherwise false
     * @since 1.0
     * */
    public boolean login(AuthorizedDataRequest request, HttpSession session){
        Optional<User> authorized = userService.findUserByLoginAndPassword(request.getLogin(), encoder.encode(request.getPassword()));
        authorized.ifPresent(user -> session.setAttribute(AUTHORIZED_USER, user));
        return authorized.isPresent();
    }

    public void logout(HttpSession session){
        session.removeAttribute(AUTHORIZED_USER);
    }

}
