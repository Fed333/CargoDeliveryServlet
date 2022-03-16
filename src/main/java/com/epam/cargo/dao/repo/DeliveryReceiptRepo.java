package com.epam.cargo.dao.repo;

import com.epam.cargo.entity.DeliveryReceipt;

import java.util.List;
import java.util.Optional;

/**
 * Repository of fetching DeliveryReceipt objects from database.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public interface DeliveryReceiptRepo extends Dao<DeliveryReceipt, Long> {
    Optional<DeliveryReceipt> findByApplicationId(Long id);

    List<DeliveryReceipt> findAllByCustomerId(Long id);

//    Page<DeliveryReceipt> findAllByCustomerId(Long id, Pageable pageable);
}
