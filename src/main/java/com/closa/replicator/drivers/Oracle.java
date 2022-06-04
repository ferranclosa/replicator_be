package com.closa.replicator.drivers;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Oracle {
    private static boolean registered = false;
    private Connection conn = null;

    public Oracle(final String connectURL, final String usr, final String pwr) throws SQLException {
        if (!Oracle.registered) {
            String driverName = "oracle.jdbc.OracleDriver";
            Driver driver = null;
            try {
                driver = (Driver) (Class.forName(driverName).newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            DriverManager.registerDriver(driver);
            Oracle.registered = true;
        }
        conn = DriverManager.getConnection(connectURL, usr, pwr);
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
