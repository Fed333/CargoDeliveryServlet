package com.epam.cargo.dao.utils.parse.impl;

import com.epam.cargo.dao.utils.parse.ObjectParser;
import com.epam.cargo.entity.BaggageType;
import com.epam.cargo.entity.DeliveredBaggage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeliveredBaggageParser implements ObjectParser<DeliveredBaggage> {

    private final DeliveredBaggageColumns columns;

    public DeliveredBaggageParser(DeliveredBaggageColumns columns) {
        this.columns = columns;
    }

    public DeliveredBaggageParser() {
        this(new DeliveredBaggageColumns());
    }

    @Override
    public DeliveredBaggage parse(ResultSet r) throws SQLException {
        Long id = r.getLong(columns.id());
        Double weight = r.getDouble(columns.weight());
        Double volume = r.getDouble(columns.volume());
        BaggageType type = BaggageType.values()[r.getInt(columns.type())];
        String description = r.getString(columns.description());

        return new DeliveredBaggage(id, weight, volume, type, description);
    }

    public static class DeliveredBaggageColumns {
        public String id(){
            return "id";
        }
        public String weight(){
           return "weight";
        }
        public String volume(){
            return "volume";
        }
        public String type(){
            return "type";
        }
        public String description(){
            return "description";
        }
    }
}
