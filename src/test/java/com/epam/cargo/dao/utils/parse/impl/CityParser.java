package com.epam.cargo.dao.utils.parse.impl;

import com.epam.cargo.dao.utils.parse.ObjectParser;
import com.epam.cargo.entity.City;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CityParser implements ObjectParser<City> {

    private final CityColumns columns;

    public CityParser(CityColumns columns) {
        this.columns = columns;
    }

    public CityParser() {
        this(new CityColumns());
    }

    @Override
    public City parse(ResultSet resultSet) throws SQLException {
        return new City(
                resultSet.getLong(columns.id()),
                resultSet.getString(columns.name()),
                resultSet.getString(columns.zipcode())
        );
    }

    public static class CityColumns {
        public String id(){
            return "id";
        }
        public String name(){
            return "name";
        }
        public String zipcode(){
            return "zipcode";
        }
    }
}
