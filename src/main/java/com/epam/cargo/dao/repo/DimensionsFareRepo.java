package com.epam.cargo.dao.repo;

import com.epam.cargo.entity.DimensionsFare;

/**
 * Repository of fetching DimensionsFare objects from database.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public interface DimensionsFareRepo extends Dao<DimensionsFare, Long> {

    DimensionsFare findFareByVolume(Integer volume);

    DimensionsFare findMaxFare();

}
