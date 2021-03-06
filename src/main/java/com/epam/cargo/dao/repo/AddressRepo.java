package com.epam.cargo.dao.repo;

import com.epam.cargo.entity.Address;
import com.epam.cargo.entity.City;

import java.util.List;
import java.util.Optional;

/**
 * Repository of fetching Addresses objects from database.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public interface AddressRepo extends Dao<Address, Long> {

    @Override
    Optional<Address> findById(Long aLong);

    @Override
    List<Address> findAll();

    Address findByHouseNumberAndCityAndStreet(String houseNumber, City city, String street);
}
