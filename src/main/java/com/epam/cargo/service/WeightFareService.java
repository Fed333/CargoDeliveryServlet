package com.epam.cargo.service;

import com.epam.cargo.dao.repo.WeightFareRepo;
import com.epam.cargo.entity.WeightFare;
import org.fed333.servletboot.annotation.Inject;
import org.fed333.servletboot.annotation.Singleton;

import java.util.List;
import java.util.Objects;

/**
 * Service class for obtaining WeightFare and calculating its price.<br>
 * @author Roman Kovalchuk
 * @see WeightFare
 * @version 1.0
 * */
@Singleton(type = Singleton.Type.LAZY)
public class WeightFareService {

    @Inject
    private WeightFareRepo weightFareRepo;

    public List<WeightFare> findAllFares() {
        return weightFareRepo.findAll();
    }

    public WeightFare findFareByWeight(Integer weight){
        requireOnlyPositive(weight);

        WeightFare fare = weightFareRepo.findFareByWeight(weight);
        if (Objects.isNull(fare)){
            fare = weightFareRepo.findMaxFare();
        }

        return fare;
    }

    /**
     * @param weight of baggage in kg
     * @return a calculated price according to given weight from weight fares table,
     * if weight is max for every max value over max value will be added a corresponding price from max fare
     * */
    public Double getPrice(Integer weight){
        double price = 0.0;
        WeightFare fare = findFareByWeight(weight);

        do {
            price += fare.getPrice();
            weight -= fare.getWeightTo();
        } while(weight != 0 && weight >= fare.getWeightFrom());

        return price;
    }

    private void requireOnlyPositive(Integer weight) {
        if (weight < 0){
            throw new IllegalArgumentException("weight cannot be null");
        }
    }

}
