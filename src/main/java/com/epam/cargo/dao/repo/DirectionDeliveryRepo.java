package com.epam.cargo.dao.repo;

import com.epam.cargo.entity.City;
import com.epam.cargo.entity.DirectionDelivery;

import java.util.List;
import java.util.Optional;

public interface DirectionDeliveryRepo extends Dao<DirectionDelivery, Long> {
    @Override
    Optional<DirectionDelivery> findById(Long id);

    @Override
    List<DirectionDelivery> findAll();

//    Page<DirectionDelivery> findAll(Pageable pageable);

    DirectionDelivery findBySenderCityAndReceiverCity(City senderCity, City receiverCity);
}
