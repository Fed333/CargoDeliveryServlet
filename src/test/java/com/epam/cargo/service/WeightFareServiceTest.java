package com.epam.cargo.service;

import com.epam.cargo.dao.repo.WeightFareRepo;
import com.epam.cargo.entity.WeightFare;
import com.epam.cargo.mock.MockApplication;
import com.epam.cargo.mock.annotation.MockBean;
import com.epam.cargo.mock.factory.MockFactory;
import com.epam.cargo.service.environment.FareDataMock;
import com.epam.cargo.service.environment.FareMockEnvironment;
import com.epam.cargo.utils.CsvFileWeightFareReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.epam.cargo.service.environment.EnvironmentConstant.WEIGHT_FARES_TABLE_CASES;
import static com.epam.cargo.utils.TestUtils.APPLICATION_PACKAGE;


class WeightFareServiceTest {

    private WeightFareService fareService;

    @MockBean
    private WeightFareRepo weightFareRepo;

    private List<WeightFare> fares;

    @BeforeEach
    public void setUp() {
        MockFactory factory = MockApplication.run(getClass(), APPLICATION_PACKAGE);
        weightFareRepo = factory.getObject(WeightFareRepo.class);
        fareService = factory.getObject(WeightFareService.class);

        fares = CsvFileWeightFareReader.readWeightFaresCsv(WEIGHT_FARES_TABLE_CASES);
    }

    private static Stream<Arguments> testGetPriceCases() {
        return Stream.of(
                Arguments.of(0.0, 20.0),
                Arguments.of(0.5, 20.0),
                Arguments.of(0.99, 20.0),
                Arguments.of(1.0, 20.0),
                Arguments.of(2.0, 20.0),
                Arguments.of(2.5, 20.0),
                Arguments.of(3.0, 30.0),
                Arguments.of(9.9, 30.0),
                Arguments.of(10.0, 60.0),
                Arguments.of(20.0, 60.0),
                Arguments.of(30.0, 120.0),
                Arguments.of(50.0, 120.0),
                Arguments.of(100.0, 150.0),
                Arguments.of(150.0, 150.0),
                Arguments.of(199.0, 150.0),
                Arguments.of(200.0, 300.0),
                Arguments.of(300.0, 450.0),
                Arguments.of(350.0, 450.0),
                Arguments.of(400.0, 600.0)
        );
    }

    @ParameterizedTest
    @MethodSource(value = "testGetPriceCases")
    public void testGetPrice(Double weight, Double price){

        FareDataMock<WeightFare> dataMock = new FareDataMock<>(fares);

        FareMockEnvironment.mockWeightFareRepo(weightFareRepo, weight, dataMock);

        Assertions.assertEquals(price, fareService.getPrice(weight.intValue()));
    }
}