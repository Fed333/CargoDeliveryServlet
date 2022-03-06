package com.epam.cargo.service;

import com.epam.cargo.dao.repo.CityRepo;
import com.epam.cargo.entity.City;
import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.infrastructure.annotation.PropertyValue;
import com.epam.cargo.infrastructure.annotation.Singleton;

import javax.annotation.PostConstruct;
import java.util.List;

@Singleton
public class CityService {

    @PropertyValue(property = "messages")
    private String messages;

    @Inject
    private CityRepo cityRepo;

    public CityService() {
    }

    public List<City> findAll(){
        return cityRepo.findAll();
    }

    /**
     * add City object to database if city with such zipcode is absent
     * @param city city for adding
     * @return true if object was successfully added, otherwise false
     * */
    public boolean addCity(City city){
        if (cityRepo.findByZipcode(city.getZipcode()).isPresent()){
            return false;
        }
        cityRepo.save(city);

        return true;
    }

}
