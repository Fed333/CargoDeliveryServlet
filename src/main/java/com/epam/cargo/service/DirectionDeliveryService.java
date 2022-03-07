package com.epam.cargo.service;

import com.epam.cargo.dao.repo.DirectionDeliveryRepo;
import com.epam.cargo.entity.DirectionDelivery;
import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.infrastructure.annotation.Singleton;

import java.util.List;

@Singleton(type = Singleton.Type.LAZY)
public class DirectionDeliveryService {

    @Inject
    @SuppressWarnings("unused")
    private DirectionDeliveryRepo directionDeliveryRepo;

    public List<DirectionDelivery> findAll(){
        return directionDeliveryRepo.findAll();
    }

}
