package com.epam.cargo.dao.utils.parse.impl;

import com.epam.cargo.dao.utils.parse.ObjectParser;
import com.epam.cargo.entity.DistanceFare;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DistanceFareParser implements ObjectParser<DistanceFare> {

    private final DistanceFareColumns columns;

    public DistanceFareParser(DistanceFareColumns columns) {
        this.columns = columns;
    }

    public DistanceFareParser() {
        this(new DistanceFareColumns());
    }

    @Override
    public DistanceFare parse(ResultSet r) throws SQLException {
        return new DistanceFare(
                r.getLong(columns.id()),
                r.getInt(columns.distanceFrom()),
                r.getInt(columns.distanceTo()),
                r.getDouble(columns.price())
        );
    }

    public static class DistanceFareColumns {
        public String id(){
            return "id";
        }
        public String distanceFrom(){
            return "distance_from";
        }
        public String distanceTo(){
            return "distance_to";
        }
        public String price(){
            return "price";
        }
    }
}
