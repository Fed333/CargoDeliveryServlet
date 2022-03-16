package com.epam.cargo.dao.repo;

import com.epam.cargo.entity.WeightFare;

/**
 * Repository of fetching WeightFare objects from database.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public interface WeightFareRepo extends Dao<WeightFare, Long> {

    WeightFare findFareByWeight(Integer weight);

    WeightFare findMaxFare();
}
