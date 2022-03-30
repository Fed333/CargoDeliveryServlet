package com.epam.cargo.dao.repo;

import com.epam.cargo.entity.City;

import java.util.List;
import java.util.Optional;

/**
 * Repository of fetching City objects from database.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public interface CityRepo extends Dao<City, Long> {
    @Override
    Optional<City> findById(Long id);

    Optional<City> findByZipcode(String zipcode);

    @Override
    List<City> findAll();
}
