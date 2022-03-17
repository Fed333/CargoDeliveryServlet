package com.epam.cargo.service;

import com.epam.cargo.dao.repo.AddressRepo;
import com.epam.cargo.entity.Address;
import com.epam.cargo.entity.City;
import com.epam.cargo.exception.NoExistingCityException;
import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.infrastructure.annotation.PropertyValue;
import com.epam.cargo.infrastructure.annotation.Singleton;

import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Service class for managing Address objects.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@Singleton
public class AddressService {


    @PropertyValue(property = "messages")
    private String messages;

    @Inject
    private AddressRepo addressRepo;

    @Inject
    private CityService cityService;

    /**
     * adding an address to database
     * add given address if it is absent, otherwise assign an id to argument without adding
     * @param address address to write in database
     * @return true if address was added, false if address already was added
     * @throws NoExistingCityException if present city of address is absent in database
     * */
    public boolean addAddress(Address address) throws NoExistingCityException {
        if (Objects.isNull(address)){
            return false;
        }

        City city = address.getCity();
        if(Objects.isNull(city) || Objects.isNull(city.getId()) || Objects.isNull(cityService.findCityById(city.getId()))){
            throw new NoExistingCityException(Optional.ofNullable(city).orElse(new City()), ResourceBundle.getBundle(messages));
        }

        Address foundAddress;
        if (!Objects.isNull(foundAddress = addressRepo.findByHouseNumberAndCityAndStreet(address.getHouseNumber(), city, address.getStreet()))){
            address.setId(foundAddress.getId());
            return false;
        }
        addressRepo.save(address);
        return true;
    }

    public void deleteAddress(Address address){
        addressRepo.delete(address);
    }

    /**
     * takes an Address object from database
     * @param id of Address
     * @return Address object
     * @throws java.util.NoSuchElementException if object is absent in database
     * */
    public Address findAddressById(Long id){
        return addressRepo.findById(id).orElseThrow();
    }

    public Address findByCityAndStreetAndHouseNumber(City city, String street, String houseNumber){
        return addressRepo.findByHouseNumberAndCityAndStreet(houseNumber, city,  street);
    }

}