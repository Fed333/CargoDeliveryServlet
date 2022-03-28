package com.epam.cargo.service;

import com.epam.cargo.dao.repo.DeliveryReceiptRepo;
import com.epam.cargo.dto.DeliveryReceiptRequest;
import com.epam.cargo.entity.DeliveryApplication;
import com.epam.cargo.entity.DeliveryReceipt;
import com.epam.cargo.entity.User;
import com.epam.cargo.exception.WrongDataException;
import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.infrastructure.annotation.Singleton;
import com.epam.cargo.infrastructure.web.data.page.Page;
import com.epam.cargo.infrastructure.web.data.pageable.Pageable;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Service class for managing {@link DeliveryReceipt} objects.<br>
 * @author Roman Kovalchuk
 * @see DeliveryReceipt
 * @version 1.0
 * */
@Singleton(type = Singleton.Type.LAZY)
public class DeliveryReceiptService {

    private static final Logger logger = Logger.getLogger(DeliveryReceiptService.class);

    @Inject
    private DeliveryReceiptRepo receiptRepo;

    @Inject
    private DeliveryApplicationService applicationService;

    public Optional<DeliveryReceipt> findById(Long id) {
        return receiptRepo.findById(id);
    }

    public List<DeliveryReceipt> findAll() {
        return receiptRepo.findAll();
    }

    public DeliveryReceipt save(DeliveryReceipt receipt) {
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

    public boolean addReceipt(DeliveryReceipt receipt) throws WrongDataException {
        if (Objects.isNull(receipt) || receiptRepo.findByApplicationId(receipt.getApplication().getId()).isPresent()){
            return false;
        }

        receiptRepo.save(receipt);
        DeliveryApplication application = receipt.getApplication();
        application.setState(DeliveryApplication.State.CONFIRMED);
        applicationService.saveApplication(application);

        printMadeReceiptSuccessfullyLog(receipt, application);
        return true;
    }

    public boolean makeReceipt(DeliveryApplication application, User manager, DeliveryReceiptRequest receiptRequest) throws WrongDataException {
        Objects.requireNonNull(application, "application cannot be null");
        Objects.requireNonNull(manager, "manager cannot be null");
        Objects.requireNonNull(receiptRequest, "receiptRequest cannot be null");

        if (!isSubmittedState(application)) {
            return false;
        }

        DeliveryReceipt receipt = ServiceUtils.createDeliveryReceipt(application, manager, receiptRequest);
        requireValidReceipt(receipt);
        return addReceipt(receipt);
    }

    private boolean isSubmittedState(DeliveryApplication application) {
        return application.getState().equals(DeliveryApplication.State.SUBMITTED);
    }

    private void requireValidReceipt(DeliveryReceipt receipt){
        Objects.requireNonNull(receipt, "Receipt cannot be null");
        Objects.requireNonNull(receipt.getApplication(), "Application cannot be null");
        Objects.requireNonNull(receipt.getCustomer(), "Customer cannot be null");
        Objects.requireNonNull(receipt.getManager(), "Manager cannot be null");
        Objects.requireNonNull(receipt.getFormationDate(), "FormationDate cannot be null");
    }

    private void printMadeReceiptSuccessfullyLog(DeliveryReceipt receipt, DeliveryApplication application) {
        User customer = application.getCustomer();
        String customerFullName = String.format("%s %s", customer.getName(), customer.getSurname());
        User manager = receipt.getManager();
        String managerFullName = String.format("%s %s", manager.getName(), manager.getSurname());
        logger.info(
                String.format("Receipt: [id=%1$d, application_id=%2$d, customer: [id=%3$d, name=%4$s], manager: [id=%5$d, name=%6$s], price=%7$f] has been made successfully.",
                        receipt.getId(), application.getId(), customer.getId(), customerFullName, manager.getId(), managerFullName, receipt.getTotalPrice()
                )
        );
    }

}
