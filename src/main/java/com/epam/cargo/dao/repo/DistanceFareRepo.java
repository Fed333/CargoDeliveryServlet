package com.epam.cargo.dao.repo;

import com.epam.cargo.entity.DistanceFare;

public interface DistanceFareRepo extends Dao<DistanceFare, Long> {

    DistanceFare findFareByDistance(Integer distance);

    DistanceFare findMaxFare();
}
