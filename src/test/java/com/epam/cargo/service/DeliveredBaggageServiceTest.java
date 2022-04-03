package com.epam.cargo.service;

import com.epam.cargo.dao.repo.DeliveredBaggageRepo;
import com.epam.cargo.dto.DeliveredBaggageRequest;
import com.epam.cargo.entity.BaggageType;
import com.epam.cargo.entity.DeliveredBaggage;
import com.epam.cargo.mock.MockApplication;
import com.epam.cargo.mock.annotation.MockBean;
import com.epam.cargo.mock.factory.MockFactory;
import com.epam.cargo.service.environment.ResourceBundleMock;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import static com.epam.cargo.utils.TestUtils.APPLICATION_PACKAGE;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class DeliveredBaggageServiceTest {

    private static ResourceBundleMock bundleMock;
    private DeliveredBaggageService baggageService;

    @MockBean
    private DeliveredBaggageRepo baggageRepo;

    @BeforeAll
    public static void globalSetUp() throws IOException {
        bundleMock = new ResourceBundleMock();
        bundleMock.mockResourceBundle();
    }

    @BeforeEach
    public void setUp(){
        MockFactory factory = MockApplication.run(getClass(), APPLICATION_PACKAGE);
        baggageService = factory.getObject(DeliveredBaggageService.class);
        baggageRepo = factory.getObject(DeliveredBaggageRepo.class);
    }

    @AfterAll
    public static void globalTearDown(){
        bundleMock.closeMock();
    }

    public static Stream<Arguments> baggageCases() {
        return Stream.of(
                Arguments.of(
                        new DeliveredBaggage(1L, 10d, 14000d, BaggageType.PERISHABLE, null)
                )
        );
    }

    public static Stream<Arguments> baggageFailSaveCases() {
        return Stream.of(
                Arguments.of(new DeliveredBaggage(null, null, 2000d, null, null)),
            Arguments.of(new DeliveredBaggage(null, 5d, null, null, null)),
            Arguments.of(new DeliveredBaggage(null, null, null, BaggageType.STANDART, null)),
            Arguments.of(new DeliveredBaggage(null, null, 1000000d,  BaggageType.OVERSIZED, null))
        );
    }

    public static Stream<Arguments> updateBaggageTestCases() {
        return Stream.of(
                Arguments.of(
                        new DeliveredBaggage(1L, 20d, 1600d, BaggageType.STANDART, null),
                        new DeliveredBaggageRequest(15d, 1300d, BaggageType.DANGEROUS, null)
                ),
                Arguments.of(
                        new DeliveredBaggage(2L, 2.3, 260d, BaggageType.STANDART, null),
                        new DeliveredBaggageRequest(3.4, 220d, BaggageType.PERISHABLE, null)
                )
        );
    }

    public static Stream<Arguments> updateBaggageNullPointerFail() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(null, new DeliveredBaggageRequest(15d, 1300d, BaggageType.DANGEROUS, null)),
                Arguments.of(new DeliveredBaggage(2L, 2.3, 260d, BaggageType.STANDART, null), null
                )
        );
    }

    @ParameterizedTest
    @MethodSource("baggageCases")
    void addBaggage(DeliveredBaggage baggage) {
        Assertions.assertTrue(baggageService.save(baggage));
        verify(baggageRepo, times(1)).save(baggage);
    }

    @Test
    public void saveNull(){
        Assertions.assertFalse(baggageService.save(null));
    }

    @ParameterizedTest
    @MethodSource("baggageFailSaveCases")
    public void saveFail(DeliveredBaggage baggage){
        Assertions.assertThrows(IllegalArgumentException.class, ()->baggageService.save(baggage));
    }

    @ParameterizedTest
    @MethodSource("updateBaggageTestCases")
    void update(DeliveredBaggage baggage, DeliveredBaggageRequest updateBaggageRequest) {
        when(baggageRepo.findById(baggage.getId())).thenReturn(Optional.of(baggage));
        baggageService.update(baggage, updateBaggageRequest);

        DeliveredBaggage expected = new DeliveredBaggage(
                baggage.getId(),
                updateBaggageRequest.getWeight(),
                updateBaggageRequest.getVolume(),
                updateBaggageRequest.getType(),
                updateBaggageRequest.getDescription()
        );

        verify(baggageRepo, times(1)).save(expected);
    }

    @ParameterizedTest
    @MethodSource("updateBaggageTestCases")
    void updateMissingBaggage(DeliveredBaggage baggage, DeliveredBaggageRequest updateBaggageRequest){
        when(baggageRepo.findById(baggage.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, ()->baggageService.update(baggage, updateBaggageRequest));
    }

    @ParameterizedTest
    @MethodSource("updateBaggageNullPointerFail")
    void updateNull(DeliveredBaggage baggage, DeliveredBaggageRequest updateBaggageRequest){
        Assertions.assertThrows(NullPointerException.class, ()->baggageService.update(baggage, updateBaggageRequest));
    }
}