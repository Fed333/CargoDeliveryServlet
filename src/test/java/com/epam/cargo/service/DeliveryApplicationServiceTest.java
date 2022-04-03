package com.epam.cargo.service;

import com.epam.cargo.dao.repo.*;
import com.epam.cargo.dao.utils.TestDataUtils;
import com.epam.cargo.dto.AddressRequest;
import com.epam.cargo.dto.DeliveryApplicationRequest;
import com.epam.cargo.entity.*;
import com.epam.cargo.exception.NoExistingCityException;
import com.epam.cargo.exception.NoExistingDirectionException;
import com.epam.cargo.exception.WrongDataException;
import com.epam.cargo.mock.MockApplication;
import com.epam.cargo.mock.annotation.MockBean;
import com.epam.cargo.mock.factory.MockFactory;
import com.epam.cargo.service.environment.EnvironmentConstant;
import com.epam.cargo.service.environment.FareDataMock;
import com.epam.cargo.service.environment.FareMockEnvironment;
import com.epam.cargo.service.environment.ResourceBundleMock;
import com.epam.cargo.utils.CsvFileDimensionsFareReader;
import com.epam.cargo.utils.CsvFileWeightFareReader;
import com.epam.cargo.utils.CsvFilesDistanceFareReader;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.epam.cargo.dao.utils.TestDataUtils.*;
import static com.epam.cargo.service.environment.EnvironmentConstant.*;
import static com.epam.cargo.utils.TestUtils.APPLICATION_PACKAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class DeliveryApplicationServiceTest {

    private static ResourceBundleMock bundleMock;
    private DeliveryApplicationService applicationService;

    @MockBean
    private DeliveryApplicationRepo deliveryApplicationRepo;

    @MockBean
    private DeliveredBaggageRepo deliveredBaggageRepo;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private CityRepo cityRepo;

    @MockBean
    private AddressRepo addressRepo;

    @MockBean
    private DeliveryCostCalculatorService costCalculatorService;

    @MockBean
    private DistanceFareRepo distanceFareRepo;

    @MockBean
    private WeightFareRepo weightFareRepo;

    @MockBean
    private DimensionsFareRepo dimensionsFareRepo;

    @MockBean
    private DeliveryReceiptRepo receiptRepo;

    @BeforeAll
    static void globalSetUp() throws IOException {
        bundleMock = new ResourceBundleMock();
        bundleMock.mockResourceBundle();
    }

    @AfterAll
    static void globalTearDown(){
        bundleMock.closeMock();
    }

    public static Stream<Arguments> applicationsToSaveFailTestCases() {
        return Stream.of(
                Arguments.of(
                        new DeliveryApplication(
                                null,
                                null,
                                getTestAddress1(),
                                getTestAddress2(),
                                getTestBaggage(),
                                testSendingDate(),
                                testReceivingDate(),
                                DeliveryApplication.State.SUBMITTED,
                                100d
                        ),
                        prepareSenderAddress().andThen(prepareReceiverAddress()),
                        IllegalArgumentException.class
                ),
                Arguments.of(
                        getTestApplication(),
                        prepareSenderAddress().andThen(prepareReceiverAddress()),
                        IllegalArgumentException.class
                ),
                Arguments.of(
                        new DeliveryApplication(
                                null,
                                getTestUser(),
                                getTestAddress1(),
                                getTestAddress2(),
                                null,
                                testSendingDate(),
                                testReceivingDate(),
                                DeliveryApplication.State.SUBMITTED,
                                100d
                        ),
                        prepareSenderAddress().andThen(prepareReceiverAddress()).andThen(prepareUser()),
                        IllegalArgumentException.class
                ),
                Arguments.of(
                        new DeliveryApplication(
                                null,
                                getTestUser(),
                                getTestAddress1(),
                                getTestAddress2(),
                                getTestBaggage(),
                                null,
                                null,
                                DeliveryApplication.State.SUBMITTED,
                                100d
                        ),
                        prepareSenderAddress().andThen(prepareReceiverAddress()).andThen(prepareUser()),
                        IllegalArgumentException.class
                ),
                Arguments.of(
                        getTestApplication(),
                        prepareUser().andThen(prepareSenderAddress()),
                        NoExistingCityException.class
                ),
                Arguments.of(
                        getTestApplication(),
                        prepareUser().andThen(prepareReceiverAddress()),
                        NoExistingCityException.class
                )
        );
    }

    @NotNull
    private static LocalDate testReceivingDate() {
        return testSendingDate().plusDays(3);
    }

    @NotNull
    private static LocalDate testSendingDate() {
        return LocalDate.now();
    }

    @BeforeEach
    void setUp() {
        MockFactory factory = MockApplication.run(getClass(), APPLICATION_PACKAGE);
        applicationService = factory.getObject(DeliveryApplicationService.class);

        deliveryApplicationRepo = factory.getObject(DeliveryApplicationRepo.class);
        deliveredBaggageRepo = factory.getObject(DeliveredBaggageRepo.class);
        userRepo = factory.getObject(UserRepo.class);
        cityRepo = factory.getObject(CityRepo.class);
        addressRepo = factory.getObject(AddressRepo.class);
        costCalculatorService = factory.getObject(DeliveryCostCalculatorService.class);
        distanceFareRepo = factory.getObject(DistanceFareRepo.class);
        weightFareRepo = factory.getObject(WeightFareRepo.class);
        dimensionsFareRepo = factory.getObject(DimensionsFareRepo.class);
        receiptRepo = factory.getObject(DeliveryReceiptRepo.class);
    }

    public static Stream<Arguments> sendApplicationTestCases() {
        return Stream.of(
                Arguments.of(
                        getTestUser(),
                        getTestApplicationRequest(),
                        prepareUser(getTestUser()).andThen(prepareCity(getTestCity1())).andThen(prepareCity(getTestCity2()))
                )
        );
    }

    public static Stream<Arguments> applicationsToSaveTestCases() {
        return Stream.of(
                Arguments.of(TestDataUtils.getTestApplication(), prepareApplication())
        );
    }

    @ParameterizedTest
    @MethodSource("sendApplicationTestCases")
    void sendApplication(
            User customer,
            DeliveryApplicationRequest request,
            Consumer<DeliveryApplicationServiceTest> prepared
    ) throws WrongDataException {
        prepared.accept(this);

        mockCostCalculatorService();

        applicationService.sendApplication(customer, request, Locale.UK);

        Mockito.verify(deliveryApplicationRepo, Mockito.times(1))
                .save(any(DeliveryApplication.class));
    }

    private void mockCostCalculatorService() throws NoExistingDirectionException {
        Mockito.when(costCalculatorService.calculateDistanceCost(any(Address.class), any(Address.class)))
                .thenReturn(0d);

        Mockito.when(costCalculatorService.calculateWeightCost(any(Double.class)))
                .thenReturn(0d);

        Mockito.when(costCalculatorService.calculateDimensionsCost(any(Double.class)))
                .thenReturn(0d);
    }

    @ParameterizedTest
    @MethodSource("applicationsToSaveTestCases")
    void saveApplication(DeliveryApplication application, BiConsumer<DeliveryApplicationServiceTest, DeliveryApplication> preparedActions) throws NoExistingCityException {

        preparedActions.accept(this, application);

        applicationService.saveApplication(application);
        Mockito.verify(deliveryApplicationRepo, Mockito.times(1)).save(application);
    }

    @ParameterizedTest
    @MethodSource("applicationsToSaveFailTestCases")
    void saveApplicationFail(DeliveryApplication application, BiConsumer<DeliveryApplication, DeliveryApplicationServiceTest> prepare, Class<? extends Exception> clazz) {
        prepare.accept(application, this);
        Assertions.assertThrows(clazz, ()->applicationService.saveApplication(application));
        Mockito.verify(deliveryApplicationRepo, Mockito.times(0)).save(any(DeliveryApplication.class));
    }

    @Test
    void findById() {
        DeliveryApplication application = getTestApplication();
        applicationService.findById(application.getId());
        verify(deliveryApplicationRepo, times(1)).findById(application.getId());
    }

    @Test
    void findAll() {
        applicationService.findAll();
        verify(deliveryApplicationRepo, times(1)).findAll();
    }

    @Test
    void findAllByUserId() {
        DeliveryApplication application = getTestApplication();
        applicationService.findAllByUserId(application.getCustomer().getId());
        verify(deliveryApplicationRepo, times(1)).findAllByUserId(application.getCustomer().getId());
    }

    @Test
    void completeApplication() {
        DeliveryApplication application = TestDataUtils.getTestApplication();
        application.setReceivingDate(LocalDate.now().minusDays(1));
        when(receiptRepo.findByApplicationId(application.getId())).thenReturn(Optional.of(getDeliveryReceiptPaid()));
        applicationService.completeApplication(application);
        application.setState(DeliveryApplication.State.COMPLETED);
        verify(deliveryApplicationRepo, times(1)).save(application);
    }

    @Test
    void rejectApplication() {
        DeliveryApplication application = TestDataUtils.getTestApplication();
        when(receiptRepo.findByApplicationId(application.getId())).thenReturn(Optional.of(getDeliveryReceiptUnpaid()));
        applicationService.rejectApplication(application);
        application.setState(DeliveryApplication.State.COMPLETED);
        verify(deliveryApplicationRepo, times(1)).save(application);
        verify(receiptRepo, times(1)).delete(any());
    }

    @Test
    void edit() throws NoExistingCityException, NoExistingDirectionException {
        DeliveryApplication application = TestDataUtils.getTestApplication();
        DeliveryApplicationRequest updatedRequest = TestDataUtils.getTestApplicationRequest();
        updatedRequest.setSenderAddress(getReceiverAddressRequest());
        updatedRequest.setReceiverAddress(getSenderAddressRequest());

        prepareUser(getTestUser())
                .andThen(prepareCity(getTestCity1()))
                .andThen(prepareCity(getTestCity2()))
                .andThen(prepareBaggage(getTestBaggage()))
                .accept(this);

        mockCostCalculatorService();

        applicationService.edit(application, updatedRequest);
        verify(deliveryApplicationRepo, times(1)).save(any());
        verify(costCalculatorService, times(1)).calculateDimensionsCost(any());
        verify(costCalculatorService, times(1)).calculateWeightCost(any());
        verify(costCalculatorService, times(1)).calculateDimensionsCost(any());
    }

    private Consumer<? super DeliveryApplicationServiceTest> prepareBaggage(DeliveredBaggage testBaggage) {
        return (t) -> {
            when(t.deliveredBaggageRepo.findById(testBaggage.getId())).thenReturn(Optional.of(testBaggage));
            when(t.deliveredBaggageRepo.save(any())).thenAnswer(
                    invocationOnMock -> invocationOnMock.getArgument(0, DeliveredBaggage.class)
            );
        };
    }

    private static Consumer<DeliveryApplicationServiceTest> prepareCity(City city){
        return test -> {
            when(test.cityRepo.findById(city.getId())).thenReturn(Optional.of(city));
            when(test.cityRepo.findByZipcode(city.getZipcode())).thenReturn(Optional.of(city));
        };
    }

    private static Consumer<DeliveryApplicationServiceTest> prepareUser(User user) {
        return test -> {
            when(test.userRepo.findById(user.getId())).thenReturn(Optional.of(user));
            when(test.userRepo.findByLogin(user.getLogin())).thenReturn(user);
            when(test.userRepo.findByLoginAndPassword(user.getLogin(), user.getPassword())).thenReturn(Optional.of(user));
            when(test.userRepo.findAll()).thenReturn(List.of(user));
        };
    }

    private static BiConsumer<DeliveryApplication, DeliveryApplicationServiceTest> prepareUser(){
        return (a, t) -> prepareUser(a.getCustomer()).accept(t);
    }

    private static BiConsumer<DeliveryApplication, DeliveryApplicationServiceTest> prepareSenderAddress() {
        return prepareAddress(DeliveryApplication::getSenderAddress);
    }

    private static BiConsumer<DeliveryApplication, DeliveryApplicationServiceTest> prepareReceiverAddress() {
        return prepareAddress(DeliveryApplication::getReceiverAddress);
    }

    private static BiConsumer<DeliveryApplication, DeliveryApplicationServiceTest> prepareAddress(Function<DeliveryApplication, Address> getAddressFunction){
        return (a, t) -> {
            Address address = getAddressFunction.apply(a);
            prepareAddress(address).accept(t);
        };
    }

    private static Consumer<DeliveryApplicationServiceTest> prepareAddress(Address address){
        return test -> {
            when(test.addressRepo.findById(address.getId())).thenReturn(Optional.of(address));
            when(test.addressRepo.findByHouseNumberAndCityAndStreet(address.getHouseNumber(), address.getCity(), address.getStreet())).thenReturn(address);
        };
    }

    private static BiConsumer<DeliveryApplicationServiceTest, DeliveryApplication> prepareApplication(){
        return (t, a) -> {
            prepareUser(a.getCustomer())
                    .andThen(prepareCity(a.getSenderAddress().getCity()))
                    .andThen(prepareCity(a.getReceiverAddress().getCity()))
                    .accept(t);
        };
    }

    private static Consumer<DeliveryApplicationServiceTest> prepareDistanceFare(){
        FareDataMock<DistanceFare> dataMock = new FareDataMock<>(CsvFilesDistanceFareReader.readDistanceFaresCsv(DISTANCE_FARES_TABLE_CASES));
        return (t) -> {
           when(t.distanceFareRepo.findFareByDistance(any())).thenAnswer(
                   invocationOnMock -> {
                       Integer distance = invocationOnMock.getArgument(0, Integer.class);
                       return dataMock.fareValueBetweenBy(distance, DistanceFare::getDistanceFrom, DistanceFare::getDistanceTo);
                   }
           );
        };
    }

    private static Consumer<DeliveryApplicationServiceTest> prepareWeightFare(){
        FareDataMock<WeightFare> dataMock = new FareDataMock<>(CsvFileWeightFareReader.readWeightFaresCsv(WEIGHT_FARES_TABLE_CASES));
        return (t) -> {
            when(t.distanceFareRepo.findFareByDistance(any())).thenAnswer(
                    invocationOnMock -> {
                        Integer distance = invocationOnMock.getArgument(0, Integer.class);
                        return dataMock.fareValueBetweenBy(distance, WeightFare::getWeightFrom, WeightFare::getWeightTo);
                    }
            );
        };
    }


    private static Consumer<DeliveryApplicationServiceTest> prepareDimensionsFare(){
        FareDataMock<DimensionsFare> dataMock = new FareDataMock<>(CsvFileDimensionsFareReader.readDimensionsFaresCsv(DIMENSIONS_FARES_TABLE_CASES));
        return (t) -> {
            when(t.distanceFareRepo.findFareByDistance(any())).thenAnswer(
                    invocationOnMock -> {
                        Integer distance = invocationOnMock.getArgument(0, Integer.class);
                        return dataMock.fareValueBetweenBy(distance, DimensionsFare::getDimensionsFrom, DimensionsFare::getDimensionsTo);
                    }
            );
        };
    }
}