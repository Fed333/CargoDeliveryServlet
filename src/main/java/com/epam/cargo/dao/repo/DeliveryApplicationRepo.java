package com.epam.cargo.dao.repo;

import com.epam.cargo.entity.DeliveryApplication;


import java.util.List;

public interface DeliveryApplicationRepo extends Dao<DeliveryApplication, Long> {

    List<DeliveryApplication> findAllByUserId(Long userId);

//    Page<DeliveryApplication> findAllByUserId(Long userId, Pageable pageable);
}
