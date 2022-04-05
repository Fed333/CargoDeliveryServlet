package com.epam.cargo.dao.repo;

import com.epam.cargo.entity.DeliveryApplication;
import org.fed333.servletboot.web.data.page.Page;
import org.fed333.servletboot.web.data.pageable.Pageable;


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
