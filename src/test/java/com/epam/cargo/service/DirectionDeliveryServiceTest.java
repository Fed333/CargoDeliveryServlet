package com.epam.cargo.service;

import com.epam.cargo.dao.repo.DirectionDeliveryRepo;
import com.epam.cargo.dto.DirectionDeliveryFilterRequest;
import com.epam.cargo.entity.City;
import com.epam.cargo.entity.DirectionDelivery;
import com.epam.cargo.mock.MockApplication;
import com.epam.cargo.utils.TestUtils;
import org.fed333.servletboot.testing.TestApplication;
import org.fed333.servletboot.testing.annotation.MockBean;
import org.fed333.servletboot.testing.context.MockApplicationContext;
import org.fed333.servletboot.web.data.sort.Order;
import org.fed333.servletboot.web.data.sort.Sort;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.*;
import java.util.stream.Stream;

import static com.epam.cargo.dao.utils.RepoTestsUtils.createDirectionDeliveryFilterRequest;
import static com.epam.cargo.utils.TestUtils.APPLICATION_PACKAGE;
import static org.fed333.servletboot.web.data.sort.Order.Direction.ASC;
import static org.fed333.servletboot.web.data.sort.Order.Direction.DESC;

public class DirectionDeliveryServiceTest {

    @SuppressWarnings("all")
    @MockBean
    private DirectionDeliveryRepo mockRepo;

    private DirectionDeliveryService directionDeliveryService;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockApplicationContext context = MockApplication.run(DirectionDeliveryServiceTest.class, APPLICATION_PACKAGE);
        directionDeliveryService = context.getObject(DirectionDeliveryService.class);
        mockRepo = context.getObject(DirectionDeliveryRepo.class);

        List<DirectionDelivery> unorderedDirections = getTestDirections();
        Collections.shuffle(unorderedDirections);
        Mockito.when(mockRepo.findAll()).thenReturn(unorderedDirections);

    }

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
            Arguments.of(getEmptyFilter(), getSortBySenderCityName(DESC), directionSenderCityNameDescComparator()),
            Arguments.of(getEmptyFilter(), getSortByReceiverCityName(DESC), directionReceiverCityNameDescComparator()),
            Arguments.of(getEmptyFilter(), getSortBySenderCityName(ASC), directionSenderCityNameAscComparator()),
            Arguments.of(getEmptyFilter(), getSortByReceiverCityName(ASC), directionReceiverCityNameAscComparator()),
            Arguments.of(getEmptyFilter(), getSortByDistance(DESC), directionDistanceDescComparator()),
            Arguments.of(getEmptyFilter(), getSortByDistance(ASC), directionDistanceAscComparator())
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
    private static Sort getEmptySort() {
        return Sort.by();
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
    private static Sort getSortBySenderCityName(Order.Direction direction) {
        return Sort.by(new Order("senderCity.name", direction));
    }

    @NotNull
    private static Object getSortByReceiverCityName(Order.Direction direction) {
        return Sort.by(new Order("receiverCity.name", direction));
    }

    @NotNull
    private static Object getSortByDistance(Order.Direction direction) {
        return Sort.by(new Order("distance", direction));
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
    void findAllWithSorting(DirectionDeliveryFilterRequest filter, Sort sort, Comparator<DirectionDelivery> comparator) {
        List<DirectionDelivery> actual = directionDeliveryService.findAll(filter, sort);
        Assertions.assertTrue(TestUtils.isSorted(actual, comparator));
    }

    @ParameterizedTest
    @MethodSource("testUnsortedCase")
    void findAllWithoutSorting(DirectionDeliveryFilterRequest filter, Sort sort) {
        List<DirectionDelivery> expected = directionDeliveryService.findAll(filter);
        List<DirectionDelivery> actual = directionDeliveryService.findAll(filter, sort);
        Assertions.assertEquals(expected, actual);
    }
}