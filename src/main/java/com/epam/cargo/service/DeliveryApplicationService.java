package com.epam.cargo.service;

import com.epam.cargo.dao.repo.DeliveryApplicationRepo;
import com.epam.cargo.dto.DeliveryApplicationRequest;
import com.epam.cargo.dto.validator.DeliveryApplicationRequestValidator;
import com.epam.cargo.entity.Address;
import com.epam.cargo.entity.DeliveredBaggage;
import com.epam.cargo.entity.DeliveryApplication;
import com.epam.cargo.entity.User;
import com.epam.cargo.exception.NoExistingCityException;
import com.epam.cargo.exception.NoExistingDirectionException;
import com.epam.cargo.exception.WrongDataException;
import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.infrastructure.annotation.PropertyValue;
import com.epam.cargo.infrastructure.annotation.Singleton;
import com.epam.cargo.infrastructure.web.data.page.Page;
import com.epam.cargo.infrastructure.web.data.pageable.Pageable;
import org.apache.log4j.Logger;

import java.util.*;

@Singleton(type = Singleton.Type.LAZY)
public class DeliveryApplicationService {

    private static final Logger logger = Logger.getLogger(DeliveryApplicationService.class);

    @Inject
    private DeliveryApplicationRepo deliveryApplicationRepo;

    @Inject
    private CityService cityService;

    @Inject
    private DeliveredBaggageService deliveredBaggageService;

    @Inject
    private UserService userService;

    @Inject
    private AddressService addressService;

    @Inject
    private DeliveryCostCalculatorService costCalculatorService;

    @Inject
    private DeliveryReceiptService receiptService;

    @PropertyValue
    private String messages;

    /**
     * Make and send delivery application to the database. Calculate price before saving
     * @param customer initiator of sending application
     * @param request data to make DeliveryApplication object
     * @return returned value of saveApplication method
     * */
    public boolean sendApplication(User customer, DeliveryApplicationRequest request, Locale locale) throws WrongDataException {
        Objects.requireNonNull(customer, "Customer object cannot be null");
        Objects.requireNonNull(request, "DeliveryApplicationRequest object cannot be null");

        ResourceBundle bundle = ResourceBundle.getBundle(messages, locale);

        DeliveryApplication application = ServiceUtils.createDeliveryApplication(customer, request, cityService, bundle);
        application.setPrice(calculatePrice(application));

        return saveApplication(application);

    }

    /**
     * Save application to the database. Doesn't automatically calculate price, takes already assigned one
     * @param application deliveryApplication to save
     * @throws NoExistingCityException any from specified addresses contains missing in the database city
     * @throws IllegalArgumentException if user doesn't exist in the database
     * @return true if application was saved successfully, false if parameter application is null
     * */
    public boolean saveApplication(DeliveryApplication application) throws NoExistingCityException {
        if (Objects.isNull(application)){
            return false;
        }
        ServiceUtils.requireExistingUser(application.getCustomer(), userService);

        Optional.ofNullable(application.getSendingDate()).orElseThrow(IllegalArgumentException::new);
        Optional.ofNullable(application.getReceivingDate()).orElseThrow(IllegalArgumentException::new);

        deliveredBaggageService.save(requireNotNullDeliveredBaggage(application.getDeliveredBaggage()));
        addressService.addAddress(requireNotNullAddress(application.getSenderAddress()));
        addressService.addAddress(requireNotNullAddress(application.getReceiverAddress()));
        deliveryApplicationRepo.save(application);
        logger.info(String.format("Delivery application: %s has been successfully made", applicationLogInfo(application)));
        return true;
    }

    /**
     * Make String for logger consists with applications info
     * @param application user's delivery application
     * @return String in format [id=(id), user=(userFullName), price=(price)]
     * */
    private static String applicationLogInfo(DeliveryApplication application){
        User customer = application.getCustomer();
        String customerFullName = String.format("%s %s", customer.getName(), customer.getSurname());
        return String.format("[id=%1$d, user=%2$s, price=%3$f]", application.getId(), customerFullName, application.getPrice());
    }

    public Double calculatePrice(DeliveryApplication application) throws NoExistingDirectionException {
        Double distanceCost = costCalculatorService.calculateDistanceCost(application.getSenderAddress(), application.getReceiverAddress());
        DeliveredBaggage deliveredBaggage = application.getDeliveredBaggage();
        Double weightCost = costCalculatorService.calculateWeightCost(deliveredBaggage.getWeight());
        Double dimensionsCost = costCalculatorService.calculateDimensionsCost(deliveredBaggage.getVolume());
        return distanceCost + weightCost + dimensionsCost;
    }

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

    public Page<DeliveryApplication> findAllByUserId(Long id, Pageable pageable) {
        return deliveryApplicationRepo.findAllByUserId(id, pageable);
    }


    private DeliveredBaggage requireNotNullDeliveredBaggage(DeliveredBaggage baggage) {
        return Optional.ofNullable(baggage).orElseThrow(IllegalArgumentException::new);
    }

    private Address requireNotNullAddress(Address application) {
        return Optional.ofNullable(application).orElseThrow(()->new IllegalArgumentException("Address in application cannot be null!"));
    }

}
