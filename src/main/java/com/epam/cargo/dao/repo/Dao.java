package com.epam.cargo.dao.repo;

import java.util.List;
import java.util.Optional;

public interface Dao<T, ID> {

    Optional<T> findById(ID id);

    List<T> findAll();

    T save(T o);

    void delete(T o);
}
