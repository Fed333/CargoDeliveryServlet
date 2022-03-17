package com.epam.cargo.dao.repo;

import com.epam.cargo.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Repository of fetching User objects from database.
 * @author Roman Kovalchuk
 * @version 1.1
 * */
public interface UserRepo extends Dao<User, Long> {

    @Override
    Optional<User> findById(Long id);

    @Override
    List<User> findAll();

    User findByLogin(String login);

    /**
     * Finds User object by login and password.<br>
     * Takes already encoded password.<br>
     * @param login User's login
     * @param password preliminary encoded User's password
     * @return Optional with found User, or {@link Optional#empty()} if user wasn't found
     * @since 1.1
     * */
    Optional<User> findByLoginAndPassword(String login, String password);
}
