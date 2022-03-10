package com.epam.cargo.service;

import com.epam.cargo.dao.repo.DirectionDeliveryRepo;
import com.epam.cargo.dto.DirectionDeliveryFilterRequest;
import com.epam.cargo.entity.City;
import com.epam.cargo.entity.DirectionDelivery;
import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.infrastructure.annotation.Singleton;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
}
