package com.epam.cargo.service;

import com.epam.cargo.dao.repo.DeliveredBaggageRepo;
import com.epam.cargo.entity.DeliveredBaggage;
import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.infrastructure.annotation.Singleton;

import java.util.List;
import java.util.Optional;

@Singleton(type = Singleton.Type.LAZY)
public class DeliveredBaggageService {

    @Inject
    private DeliveredBaggageRepo baggageRepo;

    public Optional<DeliveredBaggage> findById(Long id) {
        return baggageRepo.findById(id);
    }

    public List<DeliveredBaggage> findAll() {
        return baggageRepo.findAll();
    }

    public DeliveredBaggage save(DeliveredBaggage o) {
        return baggageRepo.save(o);
    }

    public void delete(DeliveredBaggage o) {
        baggageRepo.delete(o);
    }

}
