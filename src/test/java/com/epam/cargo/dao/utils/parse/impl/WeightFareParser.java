package com.epam.cargo.dao.utils.parse.impl;

import com.epam.cargo.dao.utils.parse.ObjectParser;
import com.epam.cargo.entity.DistanceFare;
import com.epam.cargo.entity.WeightFare;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WeightFareParser implements ObjectParser<WeightFare> {


    private final WeightFareColumns columns;

    public WeightFareParser(WeightFareColumns columns) {
        this.columns = columns;
    }

    public WeightFareParser() {
        this(new WeightFareColumns());
    }

    @Override
    public WeightFare parse(ResultSet r) throws SQLException {
        return new WeightFare(
                r.getLong(columns.id()),
                r.getInt(columns.weightFrom()),
                r.getInt(columns.weightTo()),
                r.getDouble(columns.price())
        );
    }

    public static class WeightFareColumns {
        public String id(){
            return "id";
        }
        public String weightFrom(){
            return "weight_from";
        }
        public String weightTo(){
            return "weight_to";
        }
        public String price(){
            return "price";
        }
    }
}

