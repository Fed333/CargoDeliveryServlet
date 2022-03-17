package com.epam.cargo.service;

import com.epam.cargo.dao.repo.UserRepo;
import com.epam.cargo.entity.User;
import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.infrastructure.annotation.Singleton;

import java.util.Optional;

/**
 * Service class for managing User objects.<br>
 * @author Roman Kovalchuk
 * @see User
 * @version 1.0
 * */
@Singleton
public class UserService {

    @Inject
    private UserRepo userRepo;

    public User findUserById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    public User findUserByLogin(String login) {
        return userRepo.findByLogin(login);
    }

    public void saveUser(User user) {
        userRepo.save(user);
    }

    public void deleteUser(User user){
        userRepo.delete(user);
    }

    /**
     * Finds User by login and password columns.<br>
     * Takes hash of password.
     * @param login User's login
     * @param password hashed with PasswordEncoder User's password
     * @return Optional with found User, or Optional.empty() if user wasn't found
     * @since 1.0
     * */
    public Optional<User> findUserByLoginAndPassword(String login, String password) {
        return userRepo.findByLoginAndPassword(login, password);
    }
}
