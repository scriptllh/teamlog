package com.wiseach.teamlog.db;

import com.wiseach.teamlog.Constants;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * User: Arlen Tan
 * 12-8-10 下午2:29
 */
public class PublicDBHelper {

    public static final QueryRunner QUERY_RUNNER = new QueryRunner();
    public static final String IN_PARAM_STR = "%in";

    public static Long getLongData(String sql, Object ... params) {
        return query(sql,new LongResultSetHandler(),params);
    }

    public static String getStringData(String sql, Object ... params) {
        return query(sql,new ResultSetHandler<String>(){


            public String handle(ResultSet rs) throws SQLException {
                if (rs.next()) {
                    return rs.getString(1);
                } else {
                    return null;
                }
            }
        },params);
    }

    public static boolean exist(String sql,Object... params) {
        Long count = getLongData(sql,params);

        return count!=null && count>0;
    }

    public static <T> T query(String sql,ResultSetHandler<T> handler, Object... params) {
        try {
            T t = QUERY_RUNNER.query(DBPool.getConnection(),sql,handler,params);
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DBPool.closeConnection();
        }
    }

    public static <T> T queryWithInParam(String sql,ResultSetHandler<T> handler, Object... params) {
        ProcessInParams processInParams = new ProcessInParams(sql, params).invoke();
        sql = processInParams.getSql();
        ArrayList<Object> newParams = processInParams.getNewParams();
        return query(sql,handler,newParams!=null?newParams.toArray():params);
    }

    public static int executeWithInParam(String sql, Object... params) {
        ProcessInParams processInParams = new ProcessInParams(sql, params).invoke();
        sql = processInParams.getSql();
        ArrayList<Object> newParams = processInParams.getNewParams();
        return exec(sql, newParams != null ? newParams.toArray() : params);
    }

    public static int exec(String sql,Object... params) {
        Connection c = DBPool.getConnection();

        try {
            return QUERY_RUNNER.update(c,sql,params);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DBPool.closeConnection();
        }
    }

    public static int execWithCallback(String sql,DBCallback callBack,Object... params) {
        Connection c = DBPool.getConnection();

        try {
            int rtn = QUERY_RUNNER.update(c,sql,params);
            callBack.doAction(c);
            return rtn;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DBPool.closeConnection();
        }
    }

    public static Long insert(String sql,Object... params) {
        DBCallback<Long> callback = new LongDBCallback() {
            @Override
            public void doAction(Connection connection) {
                try {
                    result = QUERY_RUNNER.query(connection,"select LAST_INSERT_ID()",new LongResultSetHandler());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        };
        execWithCallback(sql,callback,params);
        return callback.getResult();
    }

    private static class ProcessInParams {
        private String sql;
        private Object[] params;
        private ArrayList<Object> newParams;

        public ProcessInParams(String sql, Object... params) {
            this.sql = sql;
            this.params = params;
        }

        public String getSql() {
            return sql;
        }

        public ArrayList<Object> getNewParams() {
            return newParams;
        }

        public ProcessInParams invoke() {
            newParams = null;
            if (sql.contains(IN_PARAM_STR)) {
                newParams = new ArrayList<Object>();
                Object param;
                Object[] array;
                int count=0;
                for (int i = 0; i < params.length; i++) {
                    param = params[i];
                    if (param.getClass().isArray()) {
                        array = (Object[]) param;
                        StringBuilder replaceParams= new StringBuilder();
                        for (int j = 0; j < array.length; j++) {
                            newParams.add(array[j]);
                            if (j>0) replaceParams.append(Constants.COMMA_STRING);
                            replaceParams.append(Constants.QUESTION_STRING);
                        }
                        sql = sql.replace(IN_PARAM_STR +count,replaceParams.toString());
                        count ++;
                    } else {
                        newParams.add(param);
                    }
                }
            }
            return this;
        }
    }
}
