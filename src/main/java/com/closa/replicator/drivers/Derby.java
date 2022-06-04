package com.closa.replicator.drivers;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A class for connecting to the AS/400 database, using either the AS/400
 * native JDBC driver, or the AS/400 Toolbox for Java driver. It decides by
 * querying if we are running on OS/400 or not. This will represent a single
 * connection, and it will ensure the driver has first been registered.
 */
public class Derby {
    private static boolean registered = false;
    //  private static boolean nativeDriver = false;
    private Connection conn = null;

    public Derby() throws SQLException {
        String connectURL = null;
        if (!Derby.registered) {
        }
        try {
            connectURL = "jdbc:derby:C:/sandbox/HSBCQueriesVarious/1527/databases/FCC";
            String driverName = "org.apache.derby.jdbc.EmbeddedDriver";
            Driver driver = (Driver) (Class.forName(driverName).newInstance());
            DriverManager.registerDriver(driver);
        } catch (SQLException exc) {
            System.out.println("SQL Error......: " + exc.getMessage());
            System.out.println("SQL Error State: " + exc.getSQLState());
            System.out.println("SQL Error Code.: " + exc.getErrorCode());
        } catch (IllegalAccessException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        conn = DriverManager.getConnection(connectURL);
    } // end constructor

    /**
     * Disconnect from the database.
     */
    public void disconnect() {
        try {
            this.conn.close();
        } catch (SQLException exc) {
        }
    } // end disconnect

    /**
     * Return connection object
     */
    public Connection getConnection() {
        return this.conn;
    } // end getConnection
} // end class Derby
