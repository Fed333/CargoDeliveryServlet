package com.epam.cargo.service;

import com.epam.cargo.dao.repo.DeliveryReceiptRepo;
import com.epam.cargo.entity.DeliveryReceipt;
import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.infrastructure.annotation.Singleton;
import com.epam.cargo.infrastructure.web.data.page.Page;
import com.epam.cargo.infrastructure.web.data.pageable.Pageable;

import java.util.List;
import java.util.Optional;

@Singleton(type = Singleton.Type.LAZY)
public class DeliveryReceiptService {

    @Inject
    private DeliveryReceiptRepo receiptRepo;

    public Optional<DeliveryReceipt> findById(Long id) {
        return receiptRepo.findById(id);
    }

    public List<DeliveryReceipt> findAll() {
        return receiptRepo.findAll();
    }

    public DeliveryReceipt save(DeliveryReceipt receipt){
        return receiptRepo.save(receipt);
    }

    public void delete(DeliveryReceipt o) {
        receiptRepo.delete(o);
    }

    public Optional<DeliveryReceipt> findByApplicationId(Long id) {
        return receiptRepo.findByApplicationId(id);
    }

    public List<DeliveryReceipt> findAllByCustomerId(Long id) {
        return receiptRepo.findAllByCustomerId(id);
    }

    public Page<DeliveryReceipt> findAllByCustomerId(Long id, Pageable pageable) {
        return receiptRepo.findAllByCustomerId(id, pageable);
    }
}
