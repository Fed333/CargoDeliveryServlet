package com.epam.cargo.service;

import com.epam.cargo.dao.repo.CityRepo;
import com.epam.cargo.entity.City;
import com.epam.cargo.mock.MockApplication;
import com.epam.cargo.mock.annotation.MockBean;
import com.epam.cargo.mock.factory.MockFactory;
import com.epam.cargo.service.environment.ResourceBundleMock;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.epam.cargo.dao.utils.TestDataUtils.getTestCity1;
import static com.epam.cargo.dao.utils.TestDataUtils.getTestCity2;
import static com.epam.cargo.utils.TestUtils.APPLICATION_PACKAGE;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class CityServiceTest {

    private static ResourceBundleMock bundleMock;
    private CityService cityService;

    @MockBean
    private CityRepo cityRepo;

    @BeforeAll
    public static void globalSetUp() throws IOException {
        bundleMock = new ResourceBundleMock();
        bundleMock.mockResourceBundle();
    }

    @BeforeEach
    public void setUp(){
        MockFactory factory = MockApplication.run(CityServiceTest.class, APPLICATION_PACKAGE);
        cityService = factory.getObject(CityService.class);
        cityRepo = factory.getObject(CityRepo.class);
    }

    @AfterAll
    public static void globalTearDown(){
        bundleMock.closeMock();
    }

    @Test
    void findAll() {
        List<City> cities = List.of(getTestCity1(), getTestCity2());
        when(cityRepo.findAll()).thenReturn(cities);
        Assertions.assertEquals(cities, cityService.findAll());
    }

    @Test
    void addCity() {
        City city = getTestCity1();
        cityService.addCity(city);
        verify(cityRepo, times(1)).save(city);
    }

    @Test
    void findCityById() {
        City city = getTestCity1();
        when(cityRepo.findById(city.getId())).thenReturn(Optional.of(city));
        Assertions.assertEquals(city, cityService.findCityById(city.getId()));
    }

    @Test
    void findCityByZipCode() {
        City city = getTestCity1();
        when(cityRepo.findByZipcode(city.getZipcode())).thenReturn(Optional.of(city));
        Assertions.assertEquals(city, cityService.findCityByZipCode(city.getZipcode()));
    }
}