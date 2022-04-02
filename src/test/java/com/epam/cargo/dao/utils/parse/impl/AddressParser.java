package com.epam.cargo.dao.utils.parse.impl;

import com.epam.cargo.dao.utils.parse.ObjectParser;
import com.epam.cargo.entity.Address;
import com.epam.cargo.entity.City;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressParser implements ObjectParser<Address> {

    private final CityParser cityParser;

    private final AddressColumns columns;

    public AddressParser(CityParser cityParser, AddressColumns columns) {
        this.cityParser = cityParser;
        this.columns = columns;
    }

    public AddressParser() {
        this(new CityParser(), new AddressColumns());
    }

    @Override
    public Address parse(ResultSet resultSet) throws SQLException {
        return new Address(
                resultSet.getLong(columns.id()),
                parseCity(resultSet),
                resultSet.getString(columns.street()),
                resultSet.getString(columns.houseNumber())
        );
    }

    private City parseCity(ResultSet resultSet) throws SQLException {
        return cityParser.parse(resultSet);
    }

    public static class AddressColumns {
        public String id(){
            return "id";
        }
        public String street(){
            return "street";
        }
        public String houseNumber(){
            return "house_number";
        }
    }

}
