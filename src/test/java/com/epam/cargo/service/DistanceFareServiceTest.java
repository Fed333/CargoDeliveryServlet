package com.epam.cargo.service;

import com.epam.cargo.dao.repo.DistanceFareRepo;
import com.epam.cargo.entity.DistanceFare;
import com.epam.cargo.mock.MockApplication;
import com.epam.cargo.mock.annotation.MockBean;
import com.epam.cargo.mock.factory.MockFactory;
import com.epam.cargo.service.environment.FareDataMock;
import com.epam.cargo.service.environment.FareMockEnvironment;
import com.epam.cargo.service.environment.ResourceBundleMock;
import com.epam.cargo.utils.CsvFilesDistanceFareReader;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static com.epam.cargo.service.environment.EnvironmentConstant.DISTANCE_FARES_TABLE_CASES;
import static com.epam.cargo.utils.TestUtils.APPLICATION_PACKAGE;


class DistanceFareServiceTest {

    private static ResourceBundleMock bundleMock;
    private DistanceFareService fareService;

    @MockBean
    private DistanceFareRepo fareRepo;

    private List<DistanceFare> fares;

    @BeforeAll
    public static void globalSetUp() throws IOException {
        bundleMock = new ResourceBundleMock();
        bundleMock.mockResourceBundle();
    }

    @BeforeEach
    public void init() {
        MockFactory factory = MockApplication.run(getClass(), APPLICATION_PACKAGE);
        fareService = factory.getObject(DistanceFareService.class);
        fareRepo = factory.getObject(DistanceFareRepo.class);
        fares = CsvFilesDistanceFareReader.readDistanceFaresCsv(DISTANCE_FARES_TABLE_CASES);
        fares.forEach(System.out::println);
    }

    @AfterAll
    public static void globalTearDown(){
        bundleMock.closeMock();
    }

    private static Stream<Arguments> testGetPriceCases() {
        return Stream.of(
                Arguments.of(0, 30.0),
                Arguments.of(10, 30.0),
                Arguments.of(20, 50.0),
                Arguments.of(30, 50.0),
                Arguments.of(50, 80.0),
                Arguments.of(100, 80.0),
                Arguments.of(200, 150.0),
                Arguments.of(500, 150.0),
                Arguments.of(1000, 200.0),
                Arguments.of(2000, 200.0)
        );
    }

    @ParameterizedTest
    @MethodSource(value = "testGetPriceCases")
    public void testGetPrice(Integer distance, Double expectedPrice){
        FareDataMock<DistanceFare> dataMock = new FareDataMock<>(fares);
        FareMockEnvironment.mockDistanceFareRepo(fareRepo, distance, dataMock);

        Double price = fareService.findFareByDistance(distance).getPrice();
        Assertions.assertEquals(expectedPrice, price);
    }
}