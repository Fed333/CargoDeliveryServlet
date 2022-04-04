package com.epam.cargo.service;

import com.epam.cargo.dao.repo.*;
import com.epam.cargo.dto.DeliveryCostCalculatorRequest;
import com.epam.cargo.dto.DeliveryCostCalculatorResponse;
import com.epam.cargo.dto.DimensionsRequest;
import com.epam.cargo.entity.*;
import com.epam.cargo.exception.WrongDataException;
import com.epam.cargo.mock.MockApplication;
import com.epam.cargo.mock.annotation.MockBean;
import com.epam.cargo.mock.factory.MockFactory;
import com.epam.cargo.service.environment.FareDataMock;
import com.epam.cargo.service.environment.FareMockEnvironment;
import com.epam.cargo.service.environment.ResourceBundleMock;
import com.epam.cargo.utils.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.cargo.service.environment.CityZipcodesConstants.*;
import static com.epam.cargo.utils.TestUtils.APPLICATION_PACKAGE;


class DeliveryCostCalculatorServiceTest {

    private static final String PATH_TO_CSV_FILES = "src/test/resources/static/";
    private static final String CITIES_CSV = PATH_TO_CSV_FILES + "cities_table.csv";
    private static final String DIRECTIONS_CSV = PATH_TO_CSV_FILES + "directions_table.csv";
    private static final String DISTANCE_FARES_CSV = PATH_TO_CSV_FILES + "distance_fares_table.csv";
    private static final String WEIGHT_FARES_CSV = PATH_TO_CSV_FILES + "weight_fares_table.csv";
    private static final String DIMENSIONS_FARES_CSV = PATH_TO_CSV_FILES + "dimensions_fares_table.csv";
    private static ResourceBundleMock bundleMock;

    private DeliveryCostCalculatorService deliveryCostCalculatorService;

    @MockBean
    private CityRepo cityRepo;

    @MockBean
    private DirectionDeliveryRepo directionDeliveryRepo;

    @MockBean
    private DistanceFareRepo distanceFareRepo;

    @MockBean
    private WeightFareRepo weightFareRepo;

    @MockBean
    private DimensionsFareRepo dimensionsFareRepo;

    private static final Map<String, City> citiesByZipcodes = new HashMap<>();

    private static final Map<Long, City> citiesById = new HashMap<>();
    private static final List<DirectionDelivery> directions = new ArrayList<>();

    private static final List<DistanceFare> distanceFares = CsvFilesDistanceFareReader.readDistanceFaresCsv(DISTANCE_FARES_CSV);
    private static final List<WeightFare> weightFares = CsvFileWeightFareReader.readWeightFaresCsv(WEIGHT_FARES_CSV);
    private static final List<DimensionsFare> dimensionFares = CsvFileDimensionsFareReader.readDimensionsFaresCsv(DIMENSIONS_FARES_CSV);

    private static final FareDataMock<DistanceFare> distanceFareDataMock = new FareDataMock<>(distanceFares);
    private static final FareDataMock<WeightFare> weightFareDataMock = new FareDataMock<>(weightFares);
    private static final FareDataMock<DimensionsFare> dimensionsFareDataMock = new FareDataMock<>(dimensionFares);

    @BeforeAll
    public static void initTestEnvironment() throws IOException {
        bundleMock = new ResourceBundleMock();
        bundleMock.mockResourceBundle();
        CsvFilesCityReader.readCitiesCsv(CITIES_CSV, citiesById, citiesByZipcodes);
        CsvFilesDirectionDeliveryReader.readDirectionsDeliveryCsv(DIRECTIONS_CSV, citiesById, directions);
    }

    @BeforeEach
    public void setUp(){
        MockFactory factory = MockApplication.run(getClass(), APPLICATION_PACKAGE);
        deliveryCostCalculatorService = factory.getObject(DeliveryCostCalculatorService.class);
        cityRepo = factory.getObject(CityRepo.class);
        directionDeliveryRepo = factory.getObject(DirectionDeliveryRepo.class);
        distanceFareRepo = factory.getObject(DistanceFareRepo.class);
        weightFareRepo = factory.getObject(WeightFareRepo.class);
        dimensionsFareRepo = factory.getObject(DimensionsFareRepo.class);
    }

    @AfterAll
    static void globalTearDown(){
        bundleMock.closeMock();
    }

    private static Stream<Arguments> calculateCostCases() {

        return Stream.of(
                Arguments.of(
                        DeliveryCostCalculatorRequest.of(3L, 1L, DimensionsRequest.of(30.0, 20.0, 4.0), 3.0),
                        DeliveryCostCalculatorResponse.of(new City.Distance(getCity(UMAN_ZIPCODE), getCity(VINNYTSIA_ZIPCODE), 160.1, getCitiesByZipcodes(UMAN_ZIPCODE, VINNYTSIA_ZIPCODE)), 120.0
                        )
                ),
                Arguments.of(
                        DeliveryCostCalculatorRequest.of(2L, 3L, DimensionsRequest.of(100.0, 40.0, 30.0), 50.0),
                        DeliveryCostCalculatorResponse.of(new City.Distance(getCity(KYIV_ZIPCODE), getCity(UMAN_ZIPCODE), 375.9, getCitiesByZipcodes(KYIV_ZIPCODE, CHERKASY_ZIPCODE, UMAN_ZIPCODE)), 330.0)
                ),
                Arguments.of(
                        DeliveryCostCalculatorRequest.of(9L, 10L, DimensionsRequest.of(130.0, 155.0, 64.0), 200.0),
                        DeliveryCostCalculatorResponse.of(new City.Distance(getCity(KHARKIV_ZIPCODE), getCity(CHERNIVTSI_ZIPCODE), 1027.7, getCitiesByZipcodes(KHARKIV_ZIPCODE, POLTAVA_ZIPCODE, CHERKASY_ZIPCODE, UMAN_ZIPCODE, VINNYTSIA_ZIPCODE, KHMELNYTSKIY_ZIPCODE, KAMIANETS_ZIPCODE, CHERNIVTSI_ZIPCODE)), 580.0)
                ),
                Arguments.of(
                        DeliveryCostCalculatorRequest.of(2L, 1L, DimensionsRequest.of(8.0, 3.0, 0.2), 0.01),
                        DeliveryCostCalculatorResponse.of(new City.Distance(getCity(KYIV_ZIPCODE), getCity(VINNYTSIA_ZIPCODE), 268.6, getCitiesByZipcodes(KYIV_ZIPCODE, ZHYTOMYR_ZIPCODE, VINNYTSIA_ZIPCODE)), 180.0)
                )
        );
    }

    private static List<City> getCitiesByZipcodes(String... zipcodes){
        return Arrays.stream(zipcodes).map(DeliveryCostCalculatorServiceTest::getCity).collect(Collectors.toList());
    }

    private static City getCity(String zipcode) {
        return citiesByZipcodes.get(zipcode);
    }

    @ParameterizedTest
    @MethodSource(value = "calculateCostCases")
    void calculateCost(DeliveryCostCalculatorRequest request, DeliveryCostCalculatorResponse expectedResponse) throws WrongDataException {
        Mockito.doReturn(new ArrayList<>(citiesById.values()))
                .when(cityRepo)
                .findAll();

        for (City c : citiesById.values()) {
            Mockito.doReturn(Optional.of(c))
                    .when(cityRepo)
                    .findById(c.getId());
        }

        Mockito.doReturn(new ArrayList<>(directions))
                .when(directionDeliveryRepo)
                .findAll();

        FareMockEnvironment.mockDistanceFareRepo(distanceFareRepo, expectedResponse.getDistance().getDistance().intValue(), distanceFareDataMock);
        FareMockEnvironment.mockWeightFareRepo(weightFareRepo, request.getWeight(), weightFareDataMock);
        FareMockEnvironment.mockDimensionsFareRepo(dimensionsFareRepo, request.getDimensions().getVolume().intValue(), dimensionsFareDataMock);

        DeliveryCostCalculatorResponse response = deliveryCostCalculatorService.calculateCost(request, Locale.UK);
        Assertions.assertEquals(expectedResponse.getCost(), response.getCost());
        Assertions.assertEquals(expectedResponse.getDistance(), response.getDistance());
    }
}