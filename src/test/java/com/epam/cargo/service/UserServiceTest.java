package com.epam.cargo.service;

import com.epam.cargo.dao.repo.UserRepo;
import com.epam.cargo.dto.UserRequest;
import com.epam.cargo.entity.Address;
import com.epam.cargo.entity.User;
import com.epam.cargo.exception.NoExistingCityException;
import com.epam.cargo.exception.WrongDataException;
import com.epam.cargo.infrastructure.security.encoding.password.PasswordEncoder;
import com.epam.cargo.mock.MockApplication;
import com.epam.cargo.mock.annotation.MockBean;
import com.epam.cargo.mock.factory.MockFactory;
import com.epam.cargo.service.environment.ResourceBundleMock;
import com.epam.cargo.service.environment.UserMockEnvironment;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import static com.epam.cargo.utils.TestUtils.APPLICATION_PACKAGE;
import static org.mockito.ArgumentMatchers.any;


class UserServiceTest {

    private BigDecimal bonus = BigDecimal.valueOf(2000);

    private UserService userService;

    private CityService cityService;

    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private AddressService addressService;

    private static ResourceBundleMock bundleMock;

    private UserMockEnvironment mockEnvironment;

    public static Stream<Arguments> userRegistrationRequestsCases() {
        return Stream.of(
                Arguments.of(
                        new UserRequest("Ivan", "Sipalka", "PIPAS", "qwerty12345", "qwerty12345", "+380967634583", "mpostkryk@mail.com", null))
        );
    }

    public static Stream<Arguments> userRegistrationFailTestCases() {
        return Stream.of(
                Arguments.of(new UserRequest()),
                Arguments.of(new UserRequest(null, null, "Ted", "log4j2345", "log4j2345", null, null, null)),
                Arguments.of(new UserRequest(null, null, "Ted", "log4j2345", "4874kgty0", null, null, null)),
                Arguments.of(new UserRequest("Teodor", null, "Ted", "log4j2345", "log4j2345", null, null, null)),
                Arguments.of(new UserRequest(null, "Roosevelt", "Ted", "log4j2345", "log4j2345", null, null, null)),
                Arguments.of(new UserRequest("Teodor", "Roosevelt", "Ted", "log4j2345", "log4j2345", null, null, null)),
                Arguments.of(new UserRequest(null, "Klymchuk", "oLeg", "fjdhf8344", "fjdhf8344", "+380985768932", null, null)),
                Arguments.of(new UserRequest("Oleg", "Klymchuk", "oLeg", "fjdhf8344", "ff8343rgi", "+380985768932", null, null)),
                Arguments.of(new UserRequest(null, null, "Catalina", null, null, null, null, null)),
                Arguments.of(new UserRequest("Merry", "Jane", null, null, null, null, null, null)),
                Arguments.of(new UserRequest("Merry", "Jane", null, null, null, "+380986378087", "somemail@mail.com", null)),
                Arguments.of(new UserRequest("Merry", "Jane", "Lalafanfan", "pass", "pass", "+380986378087", "somemail@mail.com", null)),
                Arguments.of(new UserRequest("merry", "Jane", "Lalafanfan", "frhun349434", "frhun349434", "+380986378087", "somemail@mail.com", null)),
                Arguments.of(new UserRequest("Merry", "jane", "Lalafanfan", "frhun349434", "frhun349434", "+380986378087", "somemail@mail.com", null)),
                Arguments.of(new UserRequest("Merry32", "Ja23ne", "Lalafanfan", "frhun349434", "frhun349434", "+380986378087", "somemail@mail.com", null))
        );
    }

    @BeforeAll
    public static void globalSetUp() throws IOException {
        bundleMock = new ResourceBundleMock();
        bundleMock.mockResourceBundle();
    }

    @BeforeEach
    public void setUp(){
        MockFactory factory = MockApplication.run(getClass(), APPLICATION_PACKAGE);
        userService = factory.getObject(UserService.class);
        cityService = factory.getObject(CityService.class);
        passwordEncoder = factory.getObject(PasswordEncoder.class);
        addressService = factory.getObject(AddressService.class);
        userRepo = factory.getObject(UserRepo.class);
        try {
            Mockito.doReturn(false).when(addressService).addAddress(any());
        } catch (NoExistingCityException e) {
            e.printStackTrace();
        }
        mockEnvironment = new UserMockEnvironment();
        mockEnvironment.mockUserRepoBean(userRepo);
    }

    @AfterAll
    public static void globalTearDown(){
        bundleMock.closeMock();
    }

    @ParameterizedTest
    @MethodSource("userRegistrationRequestsCases")
    public void registerUser(UserRequest registerUserRequest) throws WrongDataException {

        User registered = userService.registerUser(registerUserRequest, Locale.UK);

        Assertions.assertNotNull(registered.getId());
        Assertions.assertEquals(bonus, registered.getCash());
        Assertions.assertEquals(registered, userService.findUserById(registered.getId()));
    }

    @Test
    void addUser() throws NoExistingCityException {

        User user = new User();
        String name = "Maksym";
        String login = "maksymko";
        String password = "sp3ng48v31";

        user.setName(name);
        user.setLogin(login);
        user.setPassword(password);

        userService.addUser(user);

        User addedUser = userService.findUserByLogin(login);

        Assertions.assertEquals(addedUser.getCash(), bonus);
        Assertions.assertEquals(user.getId(), addedUser.getId());
    }

    @ParameterizedTest
    @MethodSource("userRegistrationFailTestCases")
    public  void userRegistrationFailTest(UserRequest registerRequest){
        Assertions.assertThrows(Exception.class, ()->userService.registerUser(registerRequest, Locale.UK));
    }


    @Test
    void addUserAndDeleteThen() throws NoExistingCityException {

        User user = new User();
        String name = "User";
        String login = "admin";
        String password = "Qwerty12345";

        user.setName(name);
        user.setLogin(login);
        user.setPassword(password);

        userService.addUser(user);

        Assertions.assertEquals(password, userService.findUserByLogin(login).getPassword());
        Assertions.assertEquals(user.getId(), userService.findUserByLogin(login).getId());

        userService.deleteUser(user);

        Assertions.assertTrue(Objects.isNull(userService.findUserByLogin(login)));

    }

    @Test
    void addUserFailed(){
        User user = new User();
        Assertions.assertThrows(IllegalArgumentException.class, ()->userService.addUser(user));
    }

    @Test
    void addUserWithAddress() throws NoExistingCityException {
        String zipcodeOfVinnitsia = "21012";
        Address address = new Address(cityService.findCityByZipCode(zipcodeOfVinnitsia), "Soborna", "68");

        String name = "Ivan";
        String login = "divan0_0";
        String password = "123";

        User user = new User(name, login, password);
        user.setAddress(address);

        userService.addUser(user);

        Assertions.assertEquals(user.getId(), userService.findUserByLogin(login).getId());
        Assertions.assertFalse(Objects.isNull(userService.findUserByLogin(login).getAddress()));
    }

}