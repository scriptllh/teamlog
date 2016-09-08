package com.wiseach.teamlog.db;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;

/**
 * User: Arlen Tan
 * 12-8-10 上午11:37
 */
public class DBPool {

    private static BoneCP connectionPool;
    private static final ThreadLocal<Connection> connections= new ThreadLocal<Connection>();

    public static void startServer() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            BoneCPConfig config = new BoneCPConfig();	// create a new configuration object
//            String mysqlNumber = System.getenv("MYSQL_SERVICE_NUMBER");
//
//            String jdbcUrl = MessageFormat.format("jdbc:mysql://{0}:{1}/{2}?useUnicode=yes&characterEncoding=utf8",
//                    System.getenv("MOPAAS_MYSQL"+mysqlNumber+"_HOST"),
//                    System.getenv("MOPAAS_MYSQL"+mysqlNumber+"_PORT"),
//                    System.getenv("MOPAAS_MYSQL"+mysqlNumber+"_NAME"));
//            config.setJdbcUrl(jdbcUrl);	// set the JDBC url
//            config.setUsername(System.getenv("MOPAAS_MYSQL"+mysqlNumber+"_USER"));			// set the username
//            config.setPassword(System.getenv("MOPAAS_MYSQL"+mysqlNumber+"_PASSWORD"));				// set the password

         //   config.setJdbcUrl(System.getProperty("dbUrl"));	// set the JDBC url
         //   config.setUsername(System.getProperty("dbUser"));			// set the username
         //   config.setPassword(System.getProperty("dbPass"));				// set the password
//            LoggerFactory.getLogger(DBPool.class).info("dburl:" + System.getProperty("dbUrl") + "  dbUser:" + System.getProperty("dbUser"));

            config.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/teamlog?useUnicode=true&characterEncoding=utf8");	// set the JDBC url
            config.setUsername("root");			// set the username
            config.setPassword("root");


            config.setLogStatementsEnabled(true);

            connectionPool = new BoneCP(config); 	// setup the connection pool
            DBMonitor.initialDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopServer() {
        connectionPool.shutdown();
    }

    public static final Connection getConnection() {
        Connection c = connections.get();
        if (c==null) {
            try {
                c = connectionPool.getConnection();
                connections.set(c);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return c;
    }

    public static final void closeConnection() {
        Connection c = connections.get();
        try {
            if (c!=null && !c.isClosed()) {
                c.setAutoCommit(true);
                c.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connections.set(null);
    }
}
