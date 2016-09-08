package com.wiseach.teamlog.db;

import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Arlen Tan
 * 12-8-27 下午6:08
 */
public class LongArrayResultSetHandler implements ResultSetHandler<List<Long>> {

    public List<Long> handle(ResultSet rs) throws SQLException {
        List<Long> data = new ArrayList<Long>();
        while (rs.next()) {
            data.add(rs.getLong(1));
        }
        return data;
    }
}
