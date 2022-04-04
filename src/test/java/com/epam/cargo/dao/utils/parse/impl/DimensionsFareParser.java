package com.epam.cargo.dao.utils.parse.impl;

import com.epam.cargo.dao.utils.parse.ObjectParser;
import com.epam.cargo.entity.DimensionsFare;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DimensionsFareParser implements ObjectParser<DimensionsFare> {

    private final DimensionsFareColumns columns;

    public DimensionsFareParser(DimensionsFareColumns columns) {
        this.columns = columns;
    }

    public DimensionsFareParser() {
        this(new DimensionsFareColumns());
    }

    @Override
    public DimensionsFare parse(ResultSet r) throws SQLException {
        return new DimensionsFare(
                r.getLong(columns.id()),
                r.getInt(columns.dimensionsFrom()),
                r.getInt(columns.dimensionsTo()),
                r.getDouble(columns.price())
        );
    }

    public static class DimensionsFareColumns {
        public String id(){
            return "id";
        }
        public String dimensionsFrom(){
            return "dimensions_from";
        }
        public String dimensionsTo(){
            return "dimensions_to";
        }
        public String price(){
            return "price";
        }
    }
}
