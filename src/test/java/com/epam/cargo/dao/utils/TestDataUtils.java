package com.epam.cargo.dao.utils;

import com.epam.cargo.entity.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TestDataUtils {

    public static City getTestCity1() {
        return new City(1L, "testCity1", "111");
    }

    public static City getTestCity2() {
        return new City(2L, "testCity2", "222");
    }

    public static Address getTestAddress1() {
        return new Address(1L, getTestCity1(), "testStreet1", "testHouse1");
    }

    public static Address getTestAddress2() {
        return new Address(2L, getTestCity2(), "testStreet2", "testHouse2");
    }

    public static User getTestUser(){
        return new User(
                1L,
                "Roman",
                "Kovalchuk",
                "romanko_03",
                "pass123",
                "+380986378007",
                "my@mail.com",
                BigDecimal.valueOf(2000),
                getTestAddress1(),
                new LinkedHashSet<>(Arrays.asList(Role.USER, Role.MANAGER)),
                null,
                null
        );
    }

    public static DeliveredBaggage getTestBaggage(){
        return new DeliveredBaggage(
                1L,
                10d,
                10000d,
                BaggageType.STANDART,
                "testBaggage"
        );
    }

    public static DeliveryApplication getTestApplication(){
        return new DeliveryApplication(
                1L,
                getTestUser(),
                getTestAddress1(),
                getTestAddress2(),
                getTestBaggage(),
                LocalDate.now(),
                LocalDate.now().plusDays(3),
                DeliveryApplication.State.SUBMITTED,
                20d
        );
    }

}