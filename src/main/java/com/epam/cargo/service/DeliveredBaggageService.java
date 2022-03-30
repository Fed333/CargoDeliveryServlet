package com.epam.cargo.service;

import com.epam.cargo.dao.repo.DeliveredBaggageRepo;
import com.epam.cargo.dto.DeliveredBaggageRequest;
import com.epam.cargo.entity.DeliveredBaggage;
import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.infrastructure.annotation.Singleton;

import java.util.List;
import java.util.Objects;
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

    /**
     * Save delivered baggage in the database
     * @param deliveredBaggage baggage to add
     * @throws IllegalArgumentException whereas any of mandatory attributes are missing
     * @return true whether deliveredBaggage was saved successful, false if param is null
     * */
    public boolean save(DeliveredBaggage deliveredBaggage) {
        if (Objects.isNull(deliveredBaggage)){
            return false;
        }
        requireValidBaggage(deliveredBaggage);
        baggageRepo.save(deliveredBaggage);
        return true;
    }

    public void delete(DeliveredBaggage o) {
        baggageRepo.delete(o);
    }

    /**
     * Updates state of already existing baggage
     * @param deliveredBaggage updating baggage object
     * @param deliveredBaggageRequest updating data
     * @return updated DeliveredBaggage object
     * */
    public DeliveredBaggage update(DeliveredBaggage deliveredBaggage, DeliveredBaggageRequest deliveredBaggageRequest) {
        Objects.requireNonNull(deliveredBaggage, "Delivered Baggage from db cannot be null");
        Objects.requireNonNull(deliveredBaggageRequest, "Delivered Baggage Request cannot be null");
        if (baggageRepo.findById(deliveredBaggage.getId()).isEmpty()){
            throw new IllegalArgumentException("Param deliveredBaggage doesn't exist in db");
        }
        DeliveredBaggage updated = ServiceUtils.createDeliveredBaggage(deliveredBaggageRequest);
        updated.setId(deliveredBaggage.getId());
        return baggageRepo.save(updated);
    }

    private void requireValidBaggage(DeliveredBaggage deliveredBaggage) {
        Optional.ofNullable(deliveredBaggage.getWeight()).orElseThrow(IllegalArgumentException::new);
        Optional.ofNullable(deliveredBaggage.getVolume()).orElseThrow(IllegalArgumentException::new);
        Optional.ofNullable(deliveredBaggage.getType()).orElseThrow(IllegalArgumentException::new);
    }

}
