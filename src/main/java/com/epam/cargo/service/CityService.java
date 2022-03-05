package com.epam.cargo.service;

import com.epam.cargo.dao.repo.CityRepo;
import com.epam.cargo.entity.City;
import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.infrastructure.annotation.Singleton;

import javax.annotation.PostConstruct;
import java.util.List;

@Singleton
public class CityService {

    @Inject
    private CityRepo cityRepo;

    public CityService() {
    }

    public List<City> findAll(){
        return cityRepo.findAll();
    }

    @PostConstruct
    public void init(){
        System.out.println("CityService: [init]. CityRepo: " + cityRepo);

    }
}
