package com.epam.cargo.dao.repo;

import com.epam.cargo.entity.City;
import com.epam.cargo.entity.DirectionDelivery;

import java.util.List;
import java.util.Optional;

/**
 * DAO Repository for DirectionDelivery POJO.
 * @see Dao
 * @see DirectionDelivery
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public interface DirectionDeliveryRepo extends Dao<DirectionDelivery, Long> {
    @Override
    Optional<DirectionDelivery> findById(Long id);

    @Override
    List<DirectionDelivery> findAll();

//    Page<DirectionDelivery> findAll(Pageable pageable);

    DirectionDelivery findBySenderCityAndReceiverCity(City senderCity, City receiverCity);
}
