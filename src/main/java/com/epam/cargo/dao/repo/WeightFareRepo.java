package com.epam.cargo.dao.repo;

import com.epam.cargo.entity.WeightFare;

public interface WeightFareRepo extends Dao<WeightFare, Long> {

    WeightFare findFareByWeight(Integer weight);

    WeightFare findMaxFare();
}
