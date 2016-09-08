package com.wiseach.teamlog.db;

import java.sql.Connection;

/**
 * User: Arlen Tan
 * 12-8-28 上午10:45
 */
public interface DBCallback<T> {
    public void doAction(Connection connection);
    public T getResult();
}
