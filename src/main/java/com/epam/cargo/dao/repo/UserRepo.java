package com.epam.cargo.dao.repo;

import com.epam.cargo.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Repository of fetching User objects from database.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public interface UserRepo extends Dao<User, Long> {

    @Override
    Optional<User> findById(Long id);

    @Override
    List<User> findAll();

    User findByLogin(String login);

}
