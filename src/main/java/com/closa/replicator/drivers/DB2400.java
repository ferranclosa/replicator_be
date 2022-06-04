package com.closa.replicator.drivers;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * A class for connecting to the AS/400 database, using either the AS/400
 * native JDBC driver, or the AS/400 Toolbox for Java driver. It decides by
 * querying if we are running on OS/400 or not. This will represent a single
 * connection, and it will ensure the driver has first been registered.
 */
public class DB2400 {
    private static boolean registered = false;
    private Connection conn = null;

    /**
     * Constructor. Will register the Toolbox driver if not already registered.
     * Will then connect to the database.
     *
     * @param systemName of system to connect to
     * @param userId     for connection
     * @param password   for connection
     */
    public DB2400(final String systemName, final String userId, final String password) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        String dbName = "poitou";
//   dbName = systemName.substring(0, systemName.indexOf('.'));
        Properties info = new Properties();
        info.put("user", userId);
        info.put("password", password);
        info.put("prompt", "no");
        info.put("database name", dbName);
// info.put("decimal separator", ",");
        info.put("libraries", "hfrhubfp, converter");
        String connectURL = null;
        String driverName = null;
        Driver driver = null;
// register driver if not already
        String osName = System.getProperty("os.name");
        if (osName.equals("OS/400")) {
            driverName = "com.ibm.db2.jdbc.app.DB2Driver";
            connectURL = "jdbc:db2";
        } else {
            driverName = "com.ibm.as400.access.AS400JDBCDriver";
            connectURL = "jdbc:as400://" + systemName;
        }
        if (!DB2400.registered) {
            driver = (Driver) (Class.forName(driverName).newInstance());
            DriverManager.registerDriver(driver);
            DB2400.registered = true;
        }
        if (userId != null) {
            this.conn = DriverManager.getConnection(connectURL, info);
        } else {
            this.conn = DriverManager.getConnection(connectURL);
        }
    } // end constructor

    /**
     * Constructor two. Use this when you don't have a userId and password.
     * When running on the AS/400, this will use the current job's userId and
     * password. When running on the client, this will prompt the user for a
     * userId and password.
     *
     * @param systemName of system to connect to
     */
    public DB2400(final String systemName) throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        this(systemName, null, null);
    }

    /**
     * Constructor three. Use this when you don't have a system name, userId or
     * password. When running on the AS/400, this will use the current job's
     * userId and password. When running on the client, this will prompt the
     * user for a system name, userId and password.
     */
    public DB2400() throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        this(null, null, null);
    }

    /**
     * Disconnect from the database.
     */
    public void disconnect() throws SQLException {
        this.conn.close();
    }

    /**
     * Return connection object
     */
    public Connection getConnection() {
        return this.conn;
    }
} // end class DB2400
