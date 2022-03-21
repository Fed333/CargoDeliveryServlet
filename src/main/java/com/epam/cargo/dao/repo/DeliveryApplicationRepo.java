package com.epam.cargo.dao.repo;

import com.epam.cargo.entity.DeliveryApplication;
import com.epam.cargo.infrastructure.web.data.page.Page;
import com.epam.cargo.infrastructure.web.data.pageable.Pageable;


import java.util.List;

/**
 * Repository of fetching DeliveredApplication objects from database.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public interface DeliveryApplicationRepo extends Dao<DeliveryApplication, Long> {

    List<DeliveryApplication> findAllByUserId(Long userId);

    Page<DeliveryApplication> findAllByUserId(Long userId, Pageable pageable);
}
