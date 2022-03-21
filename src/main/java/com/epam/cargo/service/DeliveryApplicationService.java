package com.epam.cargo.service;

import com.epam.cargo.dao.repo.DeliveryApplicationRepo;
import com.epam.cargo.entity.DeliveryApplication;
import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.infrastructure.annotation.Singleton;

import java.util.List;

@Singleton(type = Singleton.Type.LAZY)
public class DeliveryApplicationService {

    @Inject
    private DeliveryApplicationRepo deliveryApplicationRepo;

    /**
     * Finds application according to the given id.
     * @param id unique identifier of application in the database
     * @return found DeliveryApplication object, if no objects are found returns null
     * */
    public DeliveryApplication findById(Long id){
        return deliveryApplicationRepo.findById(id).orElse(null);
    }

    public List<DeliveryApplication> findAll() {
        return deliveryApplicationRepo.findAll();
    }

    public List<DeliveryApplication> findAllByUserId(Long id) {
        return deliveryApplicationRepo.findAllByUserId(id);
    }

//    public Page<DeliveryApplication> findAllByUserId(Long id, Pageable pageable) {
//        return deliveryApplicationRepo.findAllByUserId(id, pageable);
//    }


}
