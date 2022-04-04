package com.epam.cargo.dao.utils.parse;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interface for parsing POJO objects from db.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public interface ObjectParser<R> {
    R parse(ResultSet r) throws SQLException;
}
