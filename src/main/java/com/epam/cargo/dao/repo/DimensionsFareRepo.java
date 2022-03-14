package com.epam.cargo.dao.repo;

import com.epam.cargo.entity.DimensionsFare;

public interface DimensionsFareRepo extends Dao<DimensionsFare, Long> {

    DimensionsFare findFareByVolume(Integer volume);

    DimensionsFare findMaxFare();

}
