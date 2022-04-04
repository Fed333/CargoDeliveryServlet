package com.epam.cargo.service;

import com.epam.cargo.dao.repo.DimensionsFareRepo;
import com.epam.cargo.entity.DimensionsFare;
import com.epam.cargo.mock.MockApplication;
import com.epam.cargo.mock.annotation.MockBean;
import com.epam.cargo.mock.factory.MockFactory;
import com.epam.cargo.service.environment.FareDataMock;
import com.epam.cargo.service.environment.FareMockEnvironment;
import com.epam.cargo.utils.CsvFileDimensionsFareReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.epam.cargo.service.environment.EnvironmentConstant.DIMENSIONS_FARES_TABLE_CASES;
import static com.epam.cargo.utils.TestUtils.APPLICATION_PACKAGE;


class DimensionsFareServiceTest {

    private DimensionsFareService fareService;

    @MockBean
    private DimensionsFareRepo dimensionsFareRepo;

    private List<DimensionsFare> fares;

    @BeforeEach
    public void init(){
        MockFactory factory = MockApplication.run(getClass(), APPLICATION_PACKAGE);
        fareService = factory.getObject(DimensionsFareService.class);
        dimensionsFareRepo = factory.getObject(DimensionsFareRepo.class);
        fares = CsvFileDimensionsFareReader.readDimensionsFaresCsv(DIMENSIONS_FARES_TABLE_CASES);
    }

    private static Stream<Arguments> testGetPriceCases() {
        return Stream.of(
                Arguments.of(0, 10.0),
                Arguments.of(1000, 10.0),
                Arguments.of(4000, 10.0),
                Arguments.of(4999, 10.0),
                Arguments.of(5000, 20.0),
                Arguments.of(20000, 35.0),
                Arguments.of(50000, 35.0),
                Arguments.of(100000, 60.0),
                Arguments.of(500000, 60.0),
                Arguments.of(1000000, 80.0),
                Arguments.of(2000000, 160.0),
                Arguments.of(2500000, 160.0),
                Arguments.of(3000000, 240.0)
        );
    }

    @ParameterizedTest
    @MethodSource(value = "testGetPriceCases")
    void testGetPrice(Integer volume, Double price) {
        FareDataMock<DimensionsFare> dataMock = new FareDataMock<>(fares);

        FareMockEnvironment.mockDimensionsFareRepo(dimensionsFareRepo, volume, dataMock);

        Assertions.assertEquals(price, fareService.getPrice(volume));
    }
}
