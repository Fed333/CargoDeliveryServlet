package com.epam.cargo.service;

import com.epam.cargo.dao.repo.DimensionsFareRepo;
import com.epam.cargo.entity.DimensionsFare;
import org.fed333.servletboot.annotation.Inject;
import org.fed333.servletboot.annotation.Singleton;

import java.util.List;
import java.util.Objects;

/**
 * Service class for obtaining DimensionsFare and calculating its price.<br>
 * @author Roman Kovalchuk
 * @see DimensionsFare
 * @version 1.0
 * */
@Singleton(type = Singleton.Type.LAZY)
public class DimensionsFareService {

    @Inject
    private DimensionsFareRepo dimensionsFareRepo;

    public List<DimensionsFare> findAllFares() {
        return dimensionsFareRepo.findAll();
    }

    public DimensionsFare findFareByVolume(Integer volume){
        requireOnlyPositive(volume);

        DimensionsFare fare = dimensionsFareRepo.findFareByVolume(volume);
        if (Objects.isNull(fare)){
            fare = dimensionsFareRepo.findMaxFare();
        }
        return fare;
    }

    /**
     * @param volume of baggage in cm^3
     * @return a calculated price according to given volume from volume fares table,
     * if volume is max for every max value over max value will be added a corresponding price from max fare
     * */
    public Double getPrice(Integer volume){
        double price = 0.0;
        DimensionsFare fare = findFareByVolume(volume);

        do {
            price += fare.getPrice();
            volume -= fare.getDimensionsTo();
        } while(volume != 0 && volume >= fare.getDimensionsFrom());

        return price;
    }

    private void requireOnlyPositive(Integer volume) {
        if (volume < 0){
            throw new IllegalArgumentException("volume cannot be negative");
        }
    }

}
