package com.epam.cargo.service;

import com.epam.cargo.dao.repo.DirectionDeliveryRepo;
import com.epam.cargo.dao.repo.impl.DirectionDeliveryRepoImpl;
import com.epam.cargo.dto.DirectionDeliveryFilterRequest;
import com.epam.cargo.dto.SortRequest;
import com.epam.cargo.dto.SortRequest.Order;
import com.epam.cargo.entity.City;
import com.epam.cargo.entity.DirectionDelivery;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Stream;

import static com.epam.cargo.dao.utils.RepoTestsUtils.createDirectionDeliveryFilterRequest;
import static com.epam.cargo.dao.utils.RepoTestsUtils.createSortRequest;
import static org.mockito.Mockito.when;

public class DirectionDeliveryServiceTest {

    private DirectionDeliveryService directionDeliveryService;

    private static final ArrayList<City> testCities = new ArrayList<>(Arrays.asList(
            new City(1L, "a", "91"),
            new City(2L, "b", "82"),
            new City(3L, "c", "73"),
            new City(4L, "d", "64"),
            new City(5L, "e", "55")
    ));

    @NotNull
    public static Stream<Arguments> testSortedCase() {
        return Stream.of(
            Arguments.of(getEmptyFilter(), getSortBySenderCityName(Order.DESC), directionSenderCityNameDescComparator()),
            Arguments.of(getEmptyFilter(), getSortByReceiverCityName(Order.DESC), directionReceiverCityNameDescComparator()),
            Arguments.of(getEmptyFilter(), getSortBySenderCityName(Order.ASC), directionSenderCityNameAscComparator()),
            Arguments.of(getEmptyFilter(), getSortByReceiverCityName(Order.ASC), directionReceiverCityNameAscComparator()),
            Arguments.of(getEmptyFilter(), getSortByDistance(Order.DESC), directionDistanceDescComparator()),
            Arguments.of(getEmptyFilter(), getSortByDistance(Order.ASC), directionDistanceAscComparator())
        );
    }

    @NotNull
    public static Stream<Arguments> testUnsortedCase() {
        return Stream.of(
                Arguments.of(getEmptyFilter(), getEmptySort()),
                Arguments.of(getEmptyFilter(), null)
        );
    }

    @NotNull
    private static SortRequest getEmptySort() {
        return new SortRequest();
    }

    @NotNull
    private static Comparator<DirectionDelivery> directionDistanceAscComparator() {
        return Comparator.comparing(DirectionDelivery::getDistance);
    }

    private static Comparator<DirectionDelivery> directionDistanceDescComparator() {
        return directionDistanceAscComparator().reversed();
    }

    @NotNull
    private static Comparator<DirectionDelivery> directionSenderCityNameAscComparator() {
        return Comparator.comparing((DirectionDelivery o) -> o.getSenderCity().getName());
    }

    @NotNull
    private static Comparator<DirectionDelivery> directionSenderCityNameDescComparator() {
        return directionSenderCityNameAscComparator().reversed();
    }

    @NotNull
    private static Comparator<DirectionDelivery> directionReceiverCityNameAscComparator() {
        return Comparator.comparing((DirectionDelivery o) -> o.getReceiverCity().getName());
    }

    @NotNull
    private static Comparator<DirectionDelivery> directionReceiverCityNameDescComparator() {
        return directionReceiverCityNameAscComparator().reversed();
    }

    @NotNull
    private static DirectionDeliveryFilterRequest getEmptyFilter() {
        return createDirectionDeliveryFilterRequest("", "");
    }

    @NotNull
    private static SortRequest getSortBySenderCityName(Order order) {
        return createSortRequest("senderCity.name", order);
    }

    @NotNull
    private static Object getSortByReceiverCityName(Order order) {
        return createSortRequest("receiverCity.name", order);
    }

    @NotNull
    private static Object getSortByDistance(Order order) {
        return createSortRequest("distance", order);
    }

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        DirectionDeliveryRepo mockRepo = Mockito.mock(DirectionDeliveryRepoImpl.class);
        List<DirectionDelivery> unorderedDirections = getTestDirections();
        Collections.shuffle(unorderedDirections);
        when(mockRepo.findAll()).thenReturn(unorderedDirections);
        directionDeliveryService = new DirectionDeliveryService();
        Field field = directionDeliveryService.getClass().getDeclaredField("directionDeliveryRepo");
        field.setAccessible(true);
        field.set(directionDeliveryService, mockRepo);
    }

    @NotNull
    private static List<DirectionDelivery> getTestDirections() {
        return Arrays.asList(
            new DirectionDelivery(1L, testCities.get(0), testCities.get(4), 100d),
            new DirectionDelivery(2L, testCities.get(1), testCities.get(2), 50d),
            new DirectionDelivery(3L, testCities.get(2), testCities.get(3), 150d),
            new DirectionDelivery(4L, testCities.get(4), testCities.get(3), 20d)
        );
    }

    @ParameterizedTest
    @MethodSource("testSortedCase")
    void findAllWithSorting(DirectionDeliveryFilterRequest filter, SortRequest sort, Comparator<DirectionDelivery> comparator) {
        List<DirectionDelivery> actual = directionDeliveryService.findAll(filter, sort);
        Assertions.assertTrue(isSorted(actual, comparator));
    }

    @ParameterizedTest
    @MethodSource("testUnsortedCase")
    void findAllWithoutSorting(DirectionDeliveryFilterRequest filter, SortRequest sort) {
        List<DirectionDelivery> expected = directionDeliveryService.findAll(filter);
        List<DirectionDelivery> actual = directionDeliveryService.findAll(filter, sort);
        Assertions.assertEquals(expected, actual);
    }

    private boolean isSorted(List<DirectionDelivery> list, Comparator<DirectionDelivery> comparator){
        Iterator<DirectionDelivery> it = list.iterator();
        if (it.hasNext()) {
            DirectionDelivery o1 = it.next();
            DirectionDelivery o2;
            while(it.hasNext()){
                o2 = it.next();
                if (comparator.compare(o1, o2) > 0){
                    return false;
                }
                o1 = o2;
            }
        }
        return true;
    }

}