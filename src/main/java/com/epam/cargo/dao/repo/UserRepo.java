package com.epam.cargo.dao.repo;

import com.epam.cargo.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends Dao<User, Long> {

    @Override
    Optional<User> findById(Long id);

    @Override
    List<User> findAll();

    User findByLogin(String login);

}
