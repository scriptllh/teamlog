package com.wiseach.teamlog.db;

import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: Arlen Tan
 * 12-8-27 下午6:08
 */
public class LongResultSetHandler implements ResultSetHandler<Long> {

    public Long handle(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return rs.getLong(1);
        } else {
            return -1l;
        }
    }
}
