package com.closa.replicator.functions;

import com.closa.replicator.domain.views.ConnectionParams;
import com.closa.replicator.drivers.DB2400;
import com.closa.replicator.drivers.MySql;
import com.closa.replicator.drivers.Oracle;

import java.sql.Connection;
import java.sql.SQLException;

public class Connector {
    public Connector() {
    }

    public Connection ConFrom(ConnectionParams js) throws SQLException {
        Connection conFrom = null;
        String sys1 = js.getSourceURL();
        String usrl = js.getSourceUser();
        String pwrl = js.getSourceCreds();
        String driverName = js.getSourceDriver();
        try {
            switch (js.getSourceSystem().toLowerCase()) {
                case "as400":
                    DB2400 db2400 = new DB2400(sys1, usrl, pwrl);
                    conFrom = db2400.getConnection();
                    break;
                case "oracle":
                    Oracle oracle = new Oracle(sys1, usrl, pwrl);
                    conFrom = oracle.getConnection();
                    break;
                case "mysql":
                    MySql mysql = new MySql(sys1, usrl, pwrl, driverName);
                    conFrom = mysql.getConnection();
                    break;
                default:
                    System.out.println("Not Supported yet");
            }
        } catch (Exception exc) {
            exc.printStackTrace();
            throw new SQLException(exc.getMessage());
        }
        return conFrom;
    }

    public Connection ConTo(ConnectionParams js) throws SQLException {
        Connection conTo = null;
        String sys1 = js.getTargetURL();
        String usrl = js.getTargetUser();
        String pwrl = js.getTargetCreds();
        String driverName = js.getTargetDriver();
        try {
            switch (js.getTargetSystem().toLowerCase()) {
                case "as400":
                    DB2400 db2400 = new DB2400(sys1, usrl, pwrl);
                    conTo = db2400.getConnection();
                    break;
                case "oracle":
                    Oracle oracle = new Oracle(sys1, usrl, pwrl);
                    conTo = oracle.getConnection();
                    break;
                case "mysql":
                    MySql mysql = new MySql(sys1, usrl, pwrl, driverName);
                    conTo = mysql.getConnection();
                    break;
                default:
                    System.out.println("Not Supported yet");
            }
        } catch (Exception exc) {
            exc.printStackTrace();
            throw new SQLException(exc.getMessage());
        }
        return conTo;
    }
}
