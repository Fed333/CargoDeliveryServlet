package com.epam.cargo.service;

import com.epam.cargo.dto.DeliveryCostCalculatorRequest;
import com.epam.cargo.dto.DeliveryCostCalculatorResponse;
import com.epam.cargo.entity.Address;
import com.epam.cargo.entity.City;
import com.epam.cargo.entity.DeliveredBaggage;
import com.epam.cargo.exception.NoExistingCityException;
import com.epam.cargo.exception.NoExistingDirectionException;
import com.epam.cargo.exception.WrongDataAttributeException;
import com.epam.cargo.exception.WrongDataException;
import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.infrastructure.annotation.PropertyValue;
import com.epam.cargo.infrastructure.annotation.Singleton;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.epam.cargo.exception.ModelErrorAttribute.CITY_DIRECTION;
import static com.epam.cargo.exception.WrongInput.INVALID_DIRECTION_SAME_CITIES;

/**
 * Service class for calculating cost of delivery.<br>
 * @author Roman Kovalchuk
 * @see DistanceFareService
 * @see WeightFareService
 * @see DimensionsFareService
 * @see DirectionDeliveryService
 * @see CityService
 * @version 1.0
 * */
@Singleton(type = Singleton.Type.LAZY)
public class DeliveryCostCalculatorService {
    @Inject
    private DistanceFareService distanceFareService;

    @Inject
    private WeightFareService weightFareService;

    @Inject
    private DimensionsFareService dimensionsFareService;

    @Inject
    private DirectionDeliveryService directionDeliveryService;

    @Inject
    private CityService cityService;

    @PropertyValue
    private String messages;

    public DeliveryCostCalculatorResponse calculateCost(DeliveryCostCalculatorRequest calculatorRequest, Locale locale) throws WrongDataException {
        Objects.requireNonNull(calculatorRequest, "calculatorRequest cannot be null");

        ResourceBundle bundle = ResourceBundle.getBundle(messages, locale);

        Long cityFromId = calculatorRequest.getCityFromId();
        Long cityToId = calculatorRequest.getCityToId();
        requireDifferentCities(cityFromId, cityToId, bundle);

        City cityFrom = requireExistingCity(cityFromId);
        City cityTo = requireExistingCity(cityToId);

        City.Distance distance = directionDeliveryService.getDistanceBetweenCities(cityFrom, cityTo, locale);

        double totalCost = calculate(distance.getDistance(), calculatorRequest.getWeight(), calculatorRequest.getDimensions().getVolume());
        return new DeliveryCostCalculatorResponse(totalCost, distance);
    }

    public Double calculateCost(DeliveredBaggage baggage, Address sender, Address receiver) throws NoExistingCityException {

        City from = requireExistingCity(sender.getCity().getId());
        City to = requireExistingCity(receiver.getCity().getId());

        Double distance = directionDeliveryService.calculateMinDistance(from, to);
        return calculate(distance, baggage.getWeight(), baggage.getVolume());
    }

    public Double calculateWeightCost(Double weight){
        return weightFareService.getPrice(weight.intValue());
    }

    public Double calculateDimensionsCost(Double volume){
        return dimensionsFareService.getPrice(volume.intValue());
    }

    /**
     * Calculate distance cost between two addresses.<br>
     * @param sender address of sender
     * @param receiver address of receiver
     * @return cost according to fare, if direction is within one city, return 0.0
     * @throws NoExistingDirectionException if direction between addresses doesn't exist in the database
     * */
    public Double calculateDistanceCost(Address sender, Address receiver) throws NoExistingDirectionException {
        City from = sender.getCity();
        City to = receiver.getCity();
        if (!Objects.equals(to.getId(), from.getId())){
            Double minDistance = directionDeliveryService.calculateMinDistance(from, to);
            if (minDistance.equals(Double.POSITIVE_INFINITY)){
                ResourceBundle bundle = ResourceBundle.getBundle(messages, Locale.UK);
                throw new NoExistingDirectionException(from, to, bundle);
            }
            return distanceFareService.getPrice(minDistance.intValue());
        }
        return 0.0;
    }
    
    private void requireDifferentCities(Long cityFromId, Long cityToId, ResourceBundle bundle) throws WrongDataException {
        if (Objects.equals(cityFromId, cityToId)){
            throw new WrongDataAttributeException(CITY_DIRECTION.getAttr(), bundle, INVALID_DIRECTION_SAME_CITIES);
        }
    }

    private City requireExistingCity(Long cityId) throws NoExistingCityException {
        City city = cityService.findCityById(cityId);
        if (Objects.isNull(city)){
            throw new NoExistingCityException();
        }
        return city;
    }

    /**
     * Calculates cost of application by parameters according to fares from db.<br>
     * @param distance distance between cities
     * @param weight weight of parcel
     * @param volume volume of parcel
     * @return total cost of delivery application
     * */
    private Double calculate(Double distance, Double weight, Double volume){
        double distanceCost = distanceFareService.getPrice(distance.intValue());
        double weightCost = weightFareService.getPrice(weight.intValue());
        double dimensionsCost = dimensionsFareService.getPrice(volume.intValue());
        return distanceCost + weightCost + dimensionsCost;
    }
}
