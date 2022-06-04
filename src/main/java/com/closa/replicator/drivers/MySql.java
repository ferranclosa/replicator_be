package com.closa.replicator.drivers;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
public class MySql {
private static boolean registered = false;
private Connection conn = null;
public MySql(final String connectURL, final String usr, final String pwr, String driverName) throws SQLException {
if (!MySql.registered) {
}
try
{	Driver driver = (Driver) (Class.forName(driverName).newInstance());
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
