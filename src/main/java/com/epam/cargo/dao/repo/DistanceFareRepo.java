package com.epam.cargo.dao.repo;

import com.epam.cargo.entity.DistanceFare;

/**
 * Repository of fetching DistanceFare objects from database.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public interface DistanceFareRepo extends Dao<DistanceFare, Long> {

    DistanceFare findFareByDistance(Integer distance);

    DistanceFare findMaxFare();
}
