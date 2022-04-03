package com.epam.cargo.service;

import com.epam.cargo.dao.repo.AddressRepo;
import com.epam.cargo.dao.repo.CityRepo;
import com.epam.cargo.dao.utils.TestDataUtils;
import com.epam.cargo.entity.Address;
import com.epam.cargo.entity.City;
import com.epam.cargo.exception.NoExistingCityException;
import com.epam.cargo.mock.MockApplication;
import com.epam.cargo.mock.annotation.MockBean;
import com.epam.cargo.mock.factory.MockFactory;
import com.epam.cargo.service.environment.ResourceBundleMock;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.epam.cargo.utils.TestUtils.APPLICATION_PACKAGE;
import static org.mockito.Mockito.*;

class AddressServiceTest {

    private static ResourceBundleMock bundleMock;
    private AddressService addressService;

    @MockBean
    private CityRepo cityRepo;

    @MockBean
    private AddressRepo addressRepo;

    @BeforeAll
    public static void globalSetUp() throws IOException {
        bundleMock = new ResourceBundleMock();
        bundleMock.mockResourceBundle();
    }

    @BeforeEach
    public void setUp() throws IOException {
        MockFactory factory = MockApplication.run(AddressServiceTest.class, APPLICATION_PACKAGE);
        addressService = factory.getObject(AddressService.class);
        addressRepo = factory.getObject(AddressRepo.class);
        cityRepo = factory.getObject(CityRepo.class);

    }

    @AfterAll
    public static void globalTearDown(){
        bundleMock.closeMock();
    }

    @Test
    void addAddress() throws NoExistingCityException {
        Address address = TestDataUtils.getTestAddress1();
        City city = address.getCity();
        when(cityRepo.findById(city.getId())).thenReturn(Optional.of(city));
        addressService.addAddress(address);
        verify(addressRepo, times(1)).save(address);
    }

    @Test
    void addAddressFail(){
        Address address = TestDataUtils.getTestAddress1();
        when(cityRepo.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(NoExistingCityException.class, () -> addressService.addAddress(address));
    }

    @Test
    void deleteAddress() {
        Address address = TestDataUtils.getTestAddress1();
        addressService.deleteAddress(address);
        verify(addressRepo, times(1)).delete(address);
    }

    @Test
    void findAddressById() {
        Address address = TestDataUtils.getTestAddress1();
        when(addressRepo.findById(address.getId())).thenReturn(Optional.of(address));
        Assertions.assertEquals(address, addressService.findAddressById(address.getId()));
    }

    @Test
    void findByCityAndStreetAndHouseNumber() {
        Address address = TestDataUtils.getTestAddress1();
        when(addressRepo.findByHouseNumberAndCityAndStreet(
                address.getHouseNumber(),
                address.getCity(),
                address.getStreet())
        ).thenReturn(address);

        Assertions.assertEquals(address, addressService.findByCityAndStreetAndHouseNumber(
                address.getCity(),
                address.getStreet(),
                address.getHouseNumber())
        );
    }
}