package com.epam.cargo.service;

import com.epam.cargo.dao.repo.DirectionDeliveryRepo;
import com.epam.cargo.dto.DirectionDeliveryFilterRequest;
import com.epam.cargo.entity.City;
import com.epam.cargo.entity.DirectionDelivery;
import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.infrastructure.annotation.Singleton;
import com.epam.cargo.infrastructure.web.data.page.Page;
import com.epam.cargo.infrastructure.web.data.page.impl.PageImpl;
import com.epam.cargo.infrastructure.web.data.pageable.Pageable;
import com.epam.cargo.infrastructure.web.data.sort.Order;
import com.epam.cargo.infrastructure.web.data.sort.Sort;
import org.jetbrains.annotations.NotNull;

import java.text.Collator;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.epam.cargo.service.ServiceUtils.ComparatorRecognizer;
import static com.epam.cargo.service.ServiceUtils.sortList;

@Singleton(type = Singleton.Type.LAZY)
public class DirectionDeliveryService {

    @Inject
    @SuppressWarnings("unused")
    private DirectionDeliveryRepo directionDeliveryRepo;

    public List<DirectionDelivery> findAll(){
        return directionDeliveryRepo.findAll();
    }

    public List<DirectionDelivery> findAll(DirectionDeliveryFilterRequest filter) {
        List<DirectionDelivery> directions = findAll();
        directions = filterDirections(filter, directions);
        return directions;
    }

    public List<DirectionDelivery> findAll(DirectionDeliveryFilterRequest filter, Sort sort){
        List<DirectionDelivery> directions = findAll(filter);
        if (Objects.nonNull(sort)) {
            ServiceUtils.sortList(directions, sort, new DirectionDeliveryComparatorRecognizer(Collator.getInstance(Locale.UK)));
        }
        return directions;
    }

    public Page<DirectionDelivery> findAll(DirectionDeliveryFilterRequest filter, Pageable pageable){
        List<DirectionDelivery> directions = findAll(filter);

        Page<DirectionDelivery> page = ServiceUtils.toPage(directions, pageable, new DirectionDeliveryComparatorRecognizer(Collator.getInstance(Locale.UK)));
        return page;
    }

    public void deleteDirection(DirectionDelivery direction) {
        directionDeliveryRepo.delete(direction);
    }

    @NotNull
    private List<DirectionDelivery> filterDirections(DirectionDeliveryFilterRequest filter, List<DirectionDelivery> directions) {
        return directions.stream()
                .filter(
                        getDirectionDeliveryPredicate(filter::getSenderCityName, DirectionDelivery::getSenderCity)
                        .and(getDirectionDeliveryPredicate(filter::getReceiverCityName, DirectionDelivery::getReceiverCity))
                ).collect(Collectors.toList());
    }

    @NotNull
    private Predicate<DirectionDelivery> getDirectionDeliveryPredicate(Supplier<String> getFilteredName, Function<DirectionDelivery, City> getCity) {
        return directionDelivery -> {
            String filteredName = getFilteredName.get();
            Pattern pattern = Pattern.compile("^" + Optional.ofNullable(filteredName).orElse(""), Pattern.CASE_INSENSITIVE);
            String name = getCity.apply(directionDelivery).getName();
            Matcher matcher = pattern.matcher(name);
            return matcher.find();
        };
    }

    /**
     * finds the smallest distance between given cities, according to direction delivery routes in database
     * @return number with represents smaller distance between cities in km
     * if no way exists return Double.POSITIVE_INFINITIVE
     * */
    public Double calculateMinDistance(City cityFrom, City cityTo){
        return CityUtils.getMinDistance(cityFrom, cityTo, findAll());
    }

    private static class DirectionDeliveryComparatorRecognizer implements ComparatorRecognizer<DirectionDelivery> {

        private final Map<String, Comparator<DirectionDelivery>> comparators;

        DirectionDeliveryComparatorRecognizer(Collator collator){
            comparators = new HashMap<>();
            comparators.put("senderCity.name", new DirectionDelivery.SenderCityNameComparator(collator));
            comparators.put("receiverCity.name", new DirectionDelivery.ReceiverCityNameComparator(collator));
            comparators.put("distance", new DirectionDelivery.DistanceComparator());
        }

        @Override
        public Comparator<DirectionDelivery> getComparator(Order order) {
            Comparator<DirectionDelivery> cmp = comparators.get(order.getProperty());
            if (!Objects.isNull(cmp) && order.isDescending()){
                cmp = cmp.reversed();
            }
            return cmp;
        }
    }

    private void requireNonNullAndSimilarCities(DirectionDelivery direction){
        City city1 = Optional.ofNullable(direction.getSenderCity()).orElseThrow();
        City city2 = Optional.ofNullable(direction.getReceiverCity()).orElseThrow();
        if (city1.equals(city2)){
            throw new IllegalArgumentException();
        }
    }

    /**
     * finds the smallest distance between given cities, according to direction delivery routes in database
     * @return City.Distance object with smallest distance and route as well
     * */
    public City.Distance getDistanceBetweenCities(City cityFrom, City cityTo, Locale locale){
        return CityUtils.getDistance(
                cityFrom,
                cityTo,
                findAll()
        );
    }

}
