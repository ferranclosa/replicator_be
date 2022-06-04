package com.closa.replicator.functions;

import com.closa.replicator.domain.WorkColumn;
import com.closa.replicator.domain.WorkIndex;
import com.closa.replicator.domain.WorkRequest;
import com.closa.replicator.domain.WorkTableDetails;
import com.closa.replicator.services.ScriptService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author A8515150
 */
@Component
public class James implements Serializable {
    private Boolean loggingInd;
    private Boolean toCSVOut;
    private String SourceTableName;
    private String SourceSchemaName;
    private String sysnI;
    private String msURL;
    private Boolean clearDataBefore;
    private Boolean dropTableBefore;
    private Boolean recreateIndexes;
    private String msSchema;
    private String msTable;
    private Integer BATCH_SIZE;
    private Boolean removeWaste;
    private Boolean putTurbo;
    private String sysuI;
    private String syspI;
    private String msUser;
    private String msPwrd;
    private String msDriver;
    private String CharacterFrom1;
    private String CharacterTo1;
    private String CharacterFrom2;
    private String CharacterTo2;
    private Boolean produceXML;
    private Boolean produceJSON;
    private Boolean produceBundle;
    private String fromDB;
    private String toDB;
    private Boolean copyData;
    private Boolean reportColumnUsage;
    private Boolean useListofFiles;
    private String fileWithList;
    private Boolean createScript;
    private String scriptFile;
    private Boolean createInsertScript;
    String requestPattern;
    String reportCSVOut;
    private Properties defaultProps = new Properties();

    /*
    /*
    constructor
    */
    public void James() {
    }

    public void setSourceTableName(String sourceTableName) {
        SourceTableName = sourceTableName;
    }

    public String getFromDB() {
        return this.fromDB;
    }

    public Boolean getClearDataBefore() {
        return this.clearDataBefore;
    }

    public void setClearDataBefore(Boolean clearDataBefore) {
        this.clearDataBefore = clearDataBefore;
    }

    public Boolean getDropTableBefore() {
        return this.dropTableBefore;
    }

    public void setDropTableBefore(Boolean dropTableBefore) {
        this.dropTableBefore = dropTableBefore;
    }

    public String getScriptFile() {
        return defaultProps.getProperty("scriptFile");
    }

    public void setScriptFile(String scriptFile) {
        this.scriptFile = scriptFile;
    }

    public Boolean getRecreateIndexes() {
        return this.recreateIndexes;
    }

    public void setRecreateIndexes(Boolean recreateIndexes) {
        this.recreateIndexes = recreateIndexes;
    }

    public void setFromDB(String fromDB) {
        this.fromDB = fromDB;
    }

    public String getToDB() {
        return this.toDB;
    }

    public void setToDB(String toDB) {
        this.toDB = toDB;
    }

    public String getReportCSVOut() {
        return this.reportCSVOut;
    }

    public void setReportCSVOut(String reportCSVOut) {
        this.reportCSVOut = reportCSVOut;
    }

    /*
    This is to put in memory all the parameters on the properties file. In a version
    with tables, it would mean reading a record with all the properties.
    Il will put into the object defaultProps.* all the properties of the
    request
    */
    public void LoadParameters() {
        FileInputStream fileInputStream = null;
        try {
// create and load default properties
            String dir = System.getProperty("user.dir");
            fileInputStream = new FileInputStream("src/main/resources/request.txt");
            try {
                defaultProps.load(fileInputStream);
            } catch (IOException ex) {
                Logger.getLogger(James.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                fileInputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(James.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(James.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(James.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String rebuildDDL(ResultSet rs, String TableName, String PK_String) throws SQLException {
        String sql;
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int numberOfColumns = rsmd.getColumnCount();
            sql = "CREATE TABLE " + getMsSchema() + "." + TableName + " ( ";
            for (int i = 0; i < numberOfColumns; i++) {
                String type;
                if (rsmd.getColumnType(i + 1) == 1) {
                    if (rsmd.getPrecision(i + 1) > 254) {
                        type = rsmd.getColumnName(i + 1)
                                + " VARCHAR  ("
                                + rsmd.getPrecision(i + 1) + ") ";
                    } else {
                        type = rsmd.getColumnName(i + 1) + " "
                                + rsmd.getColumnTypeName(i + 1) + " ("
                                + rsmd.getPrecision(i + 1) + ") ";
                    }
                } else if (rsmd.getColumnType(i + 1) == 93) {
                    type = rsmd.getColumnName(i + 1) + " "
                            + rsmd.getColumnTypeName(i + 1);
                } else if (rsmd.getColumnType(i + 1) == 12) {
                    type = rsmd.getColumnName(i + 1) + " "
                            + rsmd.getColumnTypeName(i + 1) + " ("
                            + rsmd.getPrecision(i + 1) + ") ";
                } else {
                    type = rsmd.getColumnName(i + 1) + " "
                            + rsmd.getColumnTypeName(i + 1) + " ("
                            + rsmd.getPrecision(i + 1) + "," + rsmd.getScale(i + 1)
                            + ")  ";
                }
                if (rsmd.isNullable(i + 1) == 1) {
                    type = type + " NULL ";
                } else {
                    type = type + "  NOT NULL ";
                }
                if (i + 1 < numberOfColumns) {
                    sql = sql + type + ", ";
                } else {
// to do get the PRIMARY KEY
                    if ("".equals(PK_String)) {
                        sql = sql + type + " )";
                    } else {
                        sql = sql + type + "," + PK_String + " )";
                    }
                }
            }
            sql = sql.replace("#", "_");
            sql = sql.replace("@", "_");
            return sql;
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    /*
    Pass it a WorkRequest Object and it will retruns a SQL to CREATE table, with columns, prcision and scale, plus the remarks (LABEL ON)
    */
/*
Pass it a WorkRequest Object and it will retruns a SQL to CREATE table, with columns, prcision and scale, plus the remarks (LABEL ON)
*/
/*
Pass it a WorkRequest Object and it will retruns a SQL to CREATE table, with columns, prcision and scale, plus the remarks (LABEL ON)
*/
/*
Pass it a WorkRequest Object and it will retruns a SQL to CREATE table, with columns, prcision and scale, plus the remarks (LABEL ON)
*/
    public void genBundle(WorkRequest rq) throws FileNotFoundException, IOException {
        Properties Bundle = new Properties();
        FileInputStream fis = null;
        FileOutputStream fos = null;
        String dir = System.getProperty("user.dir");
        String fileName = dir + "\\" + this.getMsTable().toLowerCase() + ".properties";
        fos = new FileOutputStream(fileName);
        Properties p = new Properties();
        fis = new FileInputStream(fileName);
// p.load(fis);
        ArrayList<WorkColumn> cl0 = new ArrayList<WorkColumn>();
        cl0.addAll(rq.getRequestWorkTable().getTableWorkColumns());
        WorkColumn cl = new WorkColumn();
        for (int i = 0; i < rq.getRequestWorkTable().getTableWorkColumns().size(); i++) {
            cl = cl0.get(i);
            p.setProperty(cl.getColumnName(), cl.getColumnLabel());
        }
        p.store(fos, fileName);
        fos.close();
    }

    public void copyData(Connection conFrom, Connection conTo, James js) throws SQLException {
        Statement stmt = conTo.createStatement();
        if (this.getClearDataBefore()) {
            String sql = "delete  from " + js.getMsSchema() + "." + this.getMsTable();
            stmt.execute(sql);
            System.out.println("Cleared contents from table " + this.getSourceTableName());
        }
        String sql = "select * from " + this.getSourceSchemaName() + "." + this.getSourceTableName();
        PreparedStatement queryAll = conFrom.prepareStatement(sql);
        ResultSet rs = queryAll.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int numberOfColumns = rsmd.getColumnCount();
        String lineValues = ") VALUES (";
        String lineInsert = "INSERT INTO  " + this.getMsSchema().toLowerCase() + "." + this.getMsTable().toLowerCase() + " ( ";
// headers
        for (int i = 0; i < numberOfColumns; i++) {
            if (i + 1 < numberOfColumns) {
                lineInsert = lineInsert + rsmd.getColumnName(i + 1) + ", ";
                lineValues = lineValues + "?,";
            } else {
                lineInsert = lineInsert + rsmd.getColumnName(i + 1);
                lineValues = lineValues + "?)";
            }
        }
        lineInsert = lineInsert.replace("#", "_");
        lineInsert = lineInsert.replace("@", "_");
        lineInsert = lineInsert + lineValues;
        Integer batching = 1;
        /*
         * Here we put the actual values into the Statement
         */
        System.out.println("Writting Results File...");
        PreparedStatement stmt2 = conTo.prepareStatement(lineInsert);
        conTo.setAutoCommit(false);
        while (rs.next()) {
            String what = "";
            String line = lineInsert;
            for (int i = 0; i < numberOfColumns; i++) {
                switch (rsmd.getColumnType(i + 1)) {
                    case 1:
                        what = rs.getString(i + 1).replace("'", " ");
                        stmt2.setString(i + 1, what);
                        what = "'" + what + "'";
                        break;
                    case 12:
                        what = rs.getString(i + 1).replace("'", " ");
                        stmt2.setString(i + 1, what);
                        break;
                    case 2:
                        if (rsmd.getScale(i + 1) == 0) {
                            stmt2.setBigDecimal(i + 1, rs.getBigDecimal(i + 1));
                            what = String.valueOf(rs.getBigDecimal(i + 1));
                        } else {
                            stmt2.setDouble(i + 1, rs.getDouble(i + 1));
                            what = String.valueOf(rs.getDouble(i + 1));
                        }
                        break;
                    case 3:
                        if (rsmd.getScale(i + 1) == 0) {
                            stmt2.setBigDecimal(i + 1, rs.getBigDecimal(i + 1));
                            what = String.valueOf(rs.getBigDecimal(i + 1));
                        } else {
                            stmt2.setDouble(i + 1, rs.getDouble(i + 1));
                            what = String.valueOf(rs.getDouble(i + 1));
                        }
                        break;
                    case 93:
                        try {
                            stmt2.setTimestamp(i + 1, rs.getTimestamp(i + 1));
                            what = String.valueOf(rs.getTimestamp(i + 1));
                        } catch (SQLException e) {
                            what = null;
                        }
//  stmt2.setTimestamp(i + 1, rs.getTimestamp(i + 1));
//  what = String.valueOf(rs.getTimestamp(i + 1));
                        break;
                    default:
                        what = "Uncaught type = " + rsmd.getColumnType(i + 1) + " " + String.valueOf(rs.getString(i + 1));
                }
                if (i + 1 < numberOfColumns) {
                    line = line + what + ",";
                } else {
                    line = line + what + ")";
                }
            }
            stmt2.addBatch();
            ScriptService.addToSQLScript(stmt2.toString());
            batching = batching + 1;
            try {
                if (batching > this.getBATCH_SIZE()) {
                    stmt2.executeBatch();
                    System.out.println("DML statement = " + stmt2.toString());
                    conTo.commit();
                    batching = 1;
                }
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
        stmt2.executeBatch();
        System.out.println("DML statement = " + stmt2.toString());
        conTo.commit();
    }

    public void buildCSV(ResultSet rs, String TableName) throws SQLException {
        String wsep = ";";
        String ssep = "";
        File outFileC = new File(System.getProperty("user.dir"), TableName + ".csv");
        String line;
        PrintWriter outFileStream = null;
        try {
            outFileStream = new PrintWriter(new FileOutputStream(outFileC));
        } catch (FileNotFoundException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        ResultSetMetaData rsmd = rs.getMetaData();
        int numberOfColumns = rsmd.getColumnCount();
// headers
        line = "";
        System.out.println("Writting Headers ...");
        for (int i = 0; i < numberOfColumns; i++) {
            if (rsmd.getColumnName(i + 1).length() > rsmd
                    .getColumnDisplaySize(i + 1)) {
                line = line + rsmd.getColumnName(i + 1) + ";";
            } else {
                line = line + rsmd.getColumnName(i + 1) + ";";
            }
        }
        outFileStream.println(line);
        line = "";
        System.out.println("Writting Results File...");
        while (rs.next()) {
            for (int i = 0; i < numberOfColumns; i++) {
                if (i < numberOfColumns) {
                    line = line + rs.getString(i + 1) + wsep;
                } else {
                    line = line + rs.getString(i + 1) + ssep;
                }
            }
            outFileStream.println(line);
            line = "";
        }
        outFileStream.flush();
        outFileStream.close();
    }

    public void buildCSVReport(List<String> rs, String ReportName) {
        String wsep = ";";
        File outFileC = new File(System.getProperty("user.dir"), ReportName + ".csv");
        String line;
        PrintWriter outFileStream = null;
        try {
            outFileStream = new PrintWriter(new FileOutputStream(outFileC));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        line = "";
        for (String each : rs) {
            line = each + wsep;
            outFileStream.println(line);
            line = "";
        }
        outFileStream.flush();
        outFileStream.close();
    }

    public void createCSVStruct(List<WorkTableDetails> rs, String ReportName) throws IOException, URISyntaxException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        System.out.println("Writing Reporting  with " + ReportName);
        File file = new File(System.getProperty("user.dir"), ReportName + ".csv");
        String csvFileName = file.toString();
        CsvWriter.writeCSVFileHeaders(csvFileName, rs);
        CsvWriter.writeCSVFileLine(csvFileName, rs);
        System.out.println("Exit writing Reporting with " + ReportName);
    }

    public void buildSQLReport(List<String> theSqls, String theTable) {
        String wsep = ";";
        File outFileC = new File(System.getProperty("user.dir"), theTable + ".sql");
        String line;
        PrintWriter outFileStream = null;
        try {
            outFileStream = new PrintWriter(new FileOutputStream(outFileC));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        line = "";
        for (String eachOne : theSqls) {
            line = eachOne + wsep;
            outFileStream.println(line);
            line = "";
        }
        outFileStream.flush();
        outFileStream.close();
    }

    /*
    Pass it a result set and will retrun an SQL that can be enquired to find unused
    columns (blanks and zeros). It could be expanded to do nulls, hivals lowals etc.
    Next version.
    This should be used when enquiring a DB and not interested in empty values.
    All columns for which all values are empty (space) or 0.
    */
    public String crtWasteSQL(ResultSet rs, String TableName) throws SQLException {
        String sql = " SELECT ";
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int numberOfColumns = rsmd.getColumnCount();
            for (int i = 0; i < numberOfColumns; i++) {
                sql = sql + " MAX(" + rsmd.getColumnName(i + 1) + ")";
                if (i + 1 < numberOfColumns) {
                    sql = sql + ", ";
                } else {
                    String from = null;
                    from = " FROM " + getMsSchema().toLowerCase() + "."
                            + TableName;
                    sql = sql + from;
                }
            }
            sql = sql.replace("#", "_");
            sql = sql.replace("@", "_");
            System.out.println("Finding out Waste");
            System.out.println(sql);
            return sql;
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    /*
    Pass it a connection and an SQL (see crtWasteSQL) and it will generate
    the SQL that will drop those columns that are Waste.
    IN this context, Waste means that all  numeric values are = 0 and
    all character values are empty.
    */
    public void rmvWasteSQL(Connection conn, String sql, String each) throws SQLException {
        String innerSql = "ALTER TABLE ";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();
        int numberOfColumns = rsmd.getColumnCount();
        innerSql = innerSql + getMsSchema() + "." + each;
        String fldName;
        Integer fldType;
        String fldValueA;
        Double fldValueN;
        Boolean doit = false;
        if (rs.next()) {
            try {
                for (int i = 0; i < numberOfColumns; i++) {
                    fldName = rsmd.getColumnName(i + 1);
                    fldName = fldName.replace("MAX(", " ");
                    fldName = fldName.replace(")", " ").trim();
                    fldType = rsmd.getColumnType(i + 1);
                    switch (fldType) {
                        case 1:
                        case 12:
                        case -16:
                        case -15:
                        case 9:
                            fldValueA = rs.getString(i + 1);
                            if (fldValueA.trim().length() == 0) {
                                if (!isColumnIndex(conn, each, fldName)) {
                                    innerSql = innerSql + " DROP COLUMN " + fldName;
                                    doit = true;
                                }
                            }
                            break;
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                            fldValueN = rs.getDouble(i + 1);
                            if (fldValueN == 0.0) {
                                if (!isColumnIndex(conn, each, fldName)) {
                                    innerSql = innerSql + " DROP COLUMN " + fldName;
                                    doit = true;
                                }
                            }
                            break;
                        default:
                            break;
                    }
                    if ((i < numberOfColumns) && (doit)) {
                        innerSql = innerSql + ", ";
                    }
                    doit = false;
                }
//remove the last comma
                int sm = innerSql.lastIndexOf(",");
                if (sm > 0) {
                    innerSql = innerSql.substring(0, sm);
                }
                System.out.println("Removing Waste... ");
                System.out.println(innerSql);
                stmt.executeUpdate(innerSql);
            } catch (SQLException e) {
                throw new SQLException(e.getMessage());
            }
        }
    }

    public List<String> findFieldsSameValue(ResultSet rs, Connection conn) throws SQLException {
        List<String> listOfFields = new ArrayList<>();
        List<String> listOfSQLs = new ArrayList<>();
        PreparedStatement queryAll = null;
        ResultSet eachRs = null;
        String tabletr = getSourceTableName();
        Statement stmt = conn.createStatement();
        ResultSetMetaData rsmd = rs.getMetaData();
        int numberOfColumns = rsmd.getColumnCount();
        String selectSql = "SELECT DISTINCT ";
        String fromSql = " from " + getSourceSchemaName() + "." + getSourceTableName().toLowerCase() + " group by ";
        String innerSql = null;
        String fullSql = null;
        int counter = 0;
        String fldName;
        Integer fldType;
        String fldValueA;
        Double fldValueN;
        Boolean doit = false;
        if (rs.next()) {
            try {
                for (int i = 0; i < numberOfColumns; i++) {
                    fldName = rsmd.getColumnName(i + 1);
                    fldName = fldName.replace("DISTINCT ", " ").trim();
                    fldType = rsmd.getColumnType(i + 1);
                    switch (fldType) {
                        case 1:
                        case 12:
                        case -16:
                        case -15:
                        case 9:
                            fullSql = selectSql + " " + fldName + fromSql + " " + fldName;
                            listOfSQLs.add(fullSql);
                            queryAll = conn.prepareStatement(fullSql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
                            eachRs = queryAll.executeQuery();
                            if (eachRs.last()) {
                                counter = eachRs.getRow();
                                if (counter == 1) {
                                    listOfFields.add(tabletr + "," + fldName + ", '" + eachRs.getString(1) + "'");
                                }
                            }
                            break;
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                            fullSql = selectSql + " " + fldName + fromSql + " " + fldName;
                            listOfSQLs.add(fullSql);
                            queryAll = conn.prepareStatement(fullSql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
                            eachRs = queryAll.executeQuery();
                            if (eachRs.last()) {
                                counter = eachRs.getRow();
                                if (counter == 1) {
                                    listOfFields.add(tabletr + "," + fldName + ", " + eachRs.getLong(1));
                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
            } catch (SQLException e) {
                throw new SQLException(e.getMessage());
            }
        }
        buildCSVReport(listOfFields, getSourceTableName());
        buildSQLReport(listOfSQLs, getSourceTableName());
        return listOfFields;
    }

    public List<String> findFieldsSameValue(ResultSet rs, Connection conn, String wTable) throws SQLException {
        List<String> listOfFields = new ArrayList<>();
        List<String> listOfSQLs = new ArrayList<>();
        Map<String, String> mapOfDefaults = new HashMap<>();
        PreparedStatement queryAll = null;
        ResultSet eachRs = null;
        ResultSetMetaData rsmd = rs.getMetaData();
        int numberOfColumns = rsmd.getColumnCount();
        String selectSql = "SELECT DISTINCT ";
        String fromSql = " from " + getSourceSchemaName() + "." + wTable.toLowerCase() + " group by ";
        String fullSql = null;
        int counter = 0;
        String fldName;
        Integer fldType;
        if (rs.next()) {
            try {
                for (int i = 0; i < numberOfColumns; i++) {
                    fldName = rsmd.getColumnName(i + 1);
                    fldName = fldName.replace("DISTINCT ", " ").trim();
                    fldType = rsmd.getColumnType(i + 1);
                    switch (fldType) {
                        case 1:
                        case 12:
                        case -16:
                        case -15:
                        case 9:
                            fullSql = selectSql + " " + fldName + fromSql + " " + fldName;
                            listOfSQLs.add(fullSql);
                            queryAll = conn.prepareStatement(fullSql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
                            eachRs = queryAll.executeQuery();
                            if (eachRs.last()) {
                                counter = eachRs.getRow();
                                if (counter == 1) {
                                    listOfFields.add(wTable + "," + fldName + ", '" + eachRs.getString(1) + "'");
                                    mapOfDefaults.put(fldName, eachRs.getString(1));
                                }
                            }
                            break;
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                            fullSql = selectSql + " " + fldName + fromSql + " " + fldName;
                            listOfSQLs.add(fullSql);
                            queryAll = conn.prepareStatement(fullSql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
                            eachRs = queryAll.executeQuery();
                            if (eachRs.last()) {
                                counter = eachRs.getRow();
                                if (counter == 1) {
                                    listOfFields.add(wTable + "," + fldName + ", " + eachRs.getLong(1));
                                    mapOfDefaults.put(fldName, String.valueOf(eachRs.getLong(1)));
                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
            } catch (SQLException e) {
                throw new SQLException(e.getMessage());
            }
        }
        buildCSVReport(listOfFields, wTable);
        buildSQLReport(listOfSQLs, wTable);
        return listOfFields;
    }

    public Map<String, String> findFieldDefaults(ResultSet rs, Connection conn, String wTable) throws SQLException {
        System.out.println("Finding default values with " + wTable);
        Map<String, String> mapOfDefaults = new HashMap<>();
        PreparedStatement queryAll = null;
        ResultSet eachRs = null;
        ResultSetMetaData rsmd = rs.getMetaData();
        int numberOfColumns = rsmd.getColumnCount();
        String selectSql = "SELECT DISTINCT ";
        String fromSql = " from " + getSourceSchemaName() + "." + wTable.toLowerCase() + " group by ";
        String fullSql = null;
        int counter = 0;
        String fldName;
        Integer fldType;
        if (rs.next()) {
            try {
                for (int i = 0; i < numberOfColumns; i++) {
                    fldName = rsmd.getColumnName(i + 1);
                    fldName = fldName.replace("DISTINCT ", " ").trim();
                    fldType = rsmd.getColumnType(i + 1);
                    switch (fldType) {
                        case 1:
                        case 12:
                        case -16:
                        case -15:
                        case 9:
                            fullSql = selectSql + " " + fldName + fromSql + " " + fldName;
                            queryAll = conn.prepareStatement(fullSql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
                            eachRs = queryAll.executeQuery();
                            if (eachRs.last()) {
                                counter = eachRs.getRow();
                                if (counter == 1) {
                                    if (eachRs.getString(1).trim().isEmpty()) {
                                        mapOfDefaults.put(fldName, "*BLANKS");
                                    } else {
                                        mapOfDefaults.put(fldName, eachRs.getString(1));
                                    }
                                }
                            }
                            break;
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                            fullSql = selectSql + " " + fldName + fromSql + " " + fldName;
                            queryAll = conn.prepareStatement(fullSql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
                            eachRs = queryAll.executeQuery();
                            if (eachRs.last()) {
                                counter = eachRs.getRow();
                                if (counter == 1) {
                                    mapOfDefaults.put(fldName, String.valueOf(eachRs.getLong(1)));
                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
            } catch (SQLException e) {
                throw new SQLException(e.getMessage());
            }
        }
        System.out.println("Exit finding default/sample values with " + wTable);
        return mapOfDefaults;
    }

    public Map<String, String> findFieldSample(ResultSet rs, Connection conn, String wTable) throws SQLException {
        System.out.println("Finding sample values  with " + wTable);
        Map<String, String> mapOfSamples = new HashMap<>();
        PreparedStatement queryAll = null;
        ResultSet eachRs = null;
        ResultSetMetaData rsmd = rs.getMetaData();
        int numberOfColumns = rsmd.getColumnCount();
        String selectSql = "SELECT DISTINCT ";
        String fromSql = " from " + getSourceSchemaName() + "." + wTable.toLowerCase() + " group by ";
        String fullSql = null;
        int counter = 0;
        String fldName;
        Integer fldType;
        if (rs.next()) {
            try {
                for (int i = 0; i < numberOfColumns; i++) {
                    fldName = rsmd.getColumnName(i + 1);
                    fldName = fldName.replace("DISTINCT ", " ").trim();
                    fldType = rsmd.getColumnType(i + 1);
                    switch (fldType) {
                        case 1:
                        case 12:
                        case -16:
                        case -15:
                        case 9:
                            fullSql = selectSql + " " + fldName + fromSql + " " + fldName;
                            queryAll = conn.prepareStatement(fullSql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
                            eachRs = queryAll.executeQuery();
                            if (eachRs.last()) {
                                counter = eachRs.getRow();
//if (counter == 1) {
                                mapOfSamples.put(fldName, eachRs.getString(1));
//}
                            }
                            break;
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                            fullSql = selectSql + " " + fldName + fromSql + " " + fldName;
                            queryAll = conn.prepareStatement(fullSql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
                            eachRs = queryAll.executeQuery();
                            if (eachRs.last()) {
                                counter = eachRs.getRow();
//if (counter == 1) {
                                mapOfSamples.put(fldName, String.valueOf(eachRs.getLong(1)));
//}
                            }
                            break;
                        default:
                            break;
                    }
                }
            } catch (SQLException e) {
                throw new SQLException(e.getMessage());
            }
        }
        System.out.println("Exit Finding sample values  with " + wTable);
        return mapOfSamples;
    }

    public Boolean isColumnIndex(Connection conFrom, String Table, String Column) throws SQLException {
        String SchemaName = getMsSchema();
        DatabaseMetaData dbmd = conFrom.getMetaData();
        ResultSet rs = dbmd.getIndexInfo(null, SchemaName, Table, false, false);
        Boolean more = rs.next();
        while (more) {
            String columnName = rs.getString("COLUMN_NAME");
            if (columnName == null) {
                return false;
            }
            if (Column.equalsIgnoreCase(columnName)) {
                return true;
            }
            more = rs.next();
        }
        return false;
    }

    /*
    Pass it a WorkRequest Object and it will return a Arrays of SQL to generate all indexes, including the PRIMARY KEY one
    */
    public ArrayList<String> readListofFiles(String fileWithList) {
        ArrayList<String> files = new ArrayList<>();
        FileInputStream fls = null;
        try {
// get the file
            String dir = System.getProperty("user.dir");
            fls = new FileInputStream(dir + "/src/main/resources/" + fileWithList);
            try {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(
                        new FileInputStream(dir + "/src/main/resources/" + fileWithList), StandardCharsets.UTF_8));) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        files.add(line);
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(James.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                fls.close();
            } catch (IOException ex) {
                Logger.getLogger(James.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(James.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fls.close();
            } catch (IOException ex) {
                Logger.getLogger(James.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return files;
    }

    public void setSourceSchemaName(String schemaName) {
        SourceSchemaName = schemaName;
    }

    public void setSysnI(String sysnI) {
        this.sysnI = sysnI;
    }

    public void setSysuI(String sysuI) {
        this.sysuI = sysuI;
    }

    public void setSyspI(String syspI) {
        this.syspI = syspI;
    }

    public void setMsUser(String msUser) {
        this.msUser = msUser;
    }

    public void setMsPwrd(String msPwrd) {
        this.msPwrd = msPwrd;
    }

    public void setMsURL(String msURL) {
        this.msURL = msURL;
    }

    public void setMsDriver(String msDriver) {
        this.msDriver = msDriver;
    }

    public void setMsSchema(String msSchema) {
        this.msSchema = msSchema;
    }

    public void setMsTable(String msTable) {
        this.msTable = msTable;
    }

    public void setPutTurbo(Boolean putTurbo) {
        this.putTurbo = putTurbo;
    }

    public void setBATCH_SIZE(Integer BATCH_SIZE) {
        this.BATCH_SIZE = BATCH_SIZE;
    }

    public Boolean getLoggingInd() {
        return loggingInd;
    }

    public Boolean getToCSVOut() {
        return toCSVOut;
    }

    public String getSourceTableName() {
        return this.SourceTableName;
    }

    public String getSourceSchemaName() {
        return this.SourceSchemaName;
    }

    public String getSysnI() {
        return sysnI;
    }

    public String getMsURL() {
        return msURL;
    }

    public String getMsSchema() {
        return msSchema;
    }

    public String getMsTable() {
        return msTable;
    }

    public Integer getBATCH_SIZE() {
        return BATCH_SIZE;
    }

    public Boolean getRemoveWaste() {
        return removeWaste;
    }

    public Boolean getPutTurbo() {
        return putTurbo;
    }

    public String getSysuI() {
        return sysuI;
    }

    public String getSyspI() {
        return syspI;
    }

    public String getMsUser() {
        return msUser;
    }

    public String getMsPwrd() {
        return msPwrd;
    }

    public String getMsDriver() {
        return msDriver;
    }

    public String getCharacterFrom2() {
        return CharacterFrom2;
    }

    public String getCharacterTo2() {
        return CharacterTo2;
    }

    public Boolean getProduceXML() {
        return produceXML;
    }

    public Boolean getProduceJSON() {
        return produceJSON;
    }

    public Boolean getProduceBundle() {
        return produceBundle;
    }

    public Boolean getCopyData() {
        return copyData;
    }

    public Boolean getReportColumnUsage() {
        return reportColumnUsage;
    }

    public String getRequestPattern() {
        return requestPattern;
    }

    public String getCharacterFrom1() {
        return CharacterFrom1;
    }

    public void setCharacterFrom1(String CharacterFrom1) {
        this.CharacterFrom1 = CharacterFrom1;
    }

    public String getCharacterTo1() {
        return CharacterTo1;
    }

    public void setCharacterTo1(String CharacterTo1) {
        this.CharacterTo1 = CharacterTo1;
    }

    public void setCharacterFrom2(String CharacterFrom2) {
        this.CharacterFrom2 = CharacterFrom2;
    }

    public void setCharacterTo2(String CharacterTo2) {
        this.CharacterTo2 = CharacterTo2;
    }

    public Properties getDefaultProps() {
        return defaultProps;
    }

    public void setDefaultProps(Properties defaultProps) {
        this.defaultProps = defaultProps;
    }

    public void setProduceXML(Boolean produceXML) {
        this.produceXML = produceXML;
    }

    public void setProduceBundle(Boolean produceBundle) {
        this.produceBundle = produceBundle;
    }

    public Boolean getCreateInsertScript() {
        return createInsertScript;
    }

    public void setCreateInsertScript(Boolean createInsertScript) {
        this.createInsertScript = createInsertScript;
    }

    public void setProduceJSON(Boolean produceJSON) {
        this.produceJSON = produceJSON;
    }

    public void setCopyData(Boolean copyData) {
        this.copyData = copyData;
    }

    public void setReportColumnUsage(Boolean reportColumnUsage) {
        this.reportColumnUsage = reportColumnUsage;
    }

    public Boolean getUseListofFiles() {
        return this.useListofFiles;
    }

    public void setUseListofFiles(Boolean useListofFiles) {
        this.useListofFiles = useListofFiles;
    }

    public String getFileWithList() {
        return this.fileWithList;
    }

    public void setFileWithList(String fileWithList) {
        this.fileWithList = fileWithList;
    }

    public void setRequestPattern(String requestPattern) {
        this.requestPattern = requestPattern;
    }

    public void copyData(Connection conFrom, Connection conTo, James js, String each) throws SQLException {
        Statement stmt = conTo.createStatement();
        System.out.println("Entering copyData " + each);
        if (this.getClearDataBefore()) {
            String sql = "delete  from " + js.getMsSchema() + "." + each;
            stmt.execute(sql);
            System.out.println("Cleared contents from table " + each);
        }
        String sql = "select * from " + this.getSourceSchemaName() + "." + each;
        PreparedStatement queryAll = conFrom.prepareStatement(sql);
        ResultSet rs = queryAll.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int numberOfColumns = rsmd.getColumnCount();
        String lineValues = ") VALUES (";
        String lineInsert = "INSERT INTO  " + this.getMsSchema().toLowerCase() + "." + each.toLowerCase() + " ( ";
// headers
        for (int i = 0; i < numberOfColumns; i++) {
            if (i + 1 < numberOfColumns) {
                lineInsert = lineInsert + rsmd.getColumnName(i + 1) + ", ";
                lineValues = lineValues + "?,";
            } else {
                lineInsert = lineInsert + rsmd.getColumnName(i + 1);
                lineValues = lineValues + "?)";
            }
        }
        lineInsert = lineInsert.replace("#", "_");
        lineInsert = lineInsert.replace("@", "_");
        lineInsert = lineInsert + lineValues;
        Integer batching = 1;
        PreparedStatement stmt2 = conTo.prepareStatement(lineInsert);
        /*
         * Here we put the actual values into the Statement
         */
        try {
            System.out.println("Writting Results File..." + each);
            conTo.setAutoCommit(false);
            while (rs.next()) {
                String what = " ";
                String line = lineInsert;
                for (int i = 0; i < numberOfColumns; i++) {
                    switch (rsmd.getColumnType(i + 1)) {
                        case 1:
                            what = rs.getString(i + 1).replace("'", " ");
//stmt2.setString(i + 1, what.trim());
                            stmt2.setString(i + 1, what);
                            what = "'" + what + "'";
                            break;
                        case 12:
                            what = rs.getString(i + 1).replace("'", " ");
                            if (what.isEmpty() || what == null) {
                                what = " ";
                            }
                            stmt2.setString(i + 1, what);
                            break;
                        case 2:
                            if (rsmd.getScale(i + 1) == 0) {
                                stmt2.setBigDecimal(i + 1, rs.getBigDecimal(i + 1));
                                what = String.valueOf(rs.getBigDecimal(i + 1));
                            } else {
                                stmt2.setDouble(i + 1, rs.getDouble(i + 1));
                                what = String.valueOf(rs.getDouble(i + 1));
                            }
                            break;
                        case 3:
                            if (rsmd.getScale(i + 1) == 0) {
                                stmt2.setBigDecimal(i + 1, rs.getBigDecimal(i + 1));
                                what = String.valueOf(rs.getBigDecimal(i + 1));
                            } else {
                                stmt2.setDouble(i + 1, rs.getDouble(i + 1));
                                what = String.valueOf(rs.getDouble(i + 1));
                            }
                            break;
                        case 93:
                            try {
                                stmt2.setTimestamp(i + 1, rs.getTimestamp(i + 1));
                                what = String.valueOf(rs.getTimestamp(i + 1));
                            } catch (SQLException e) {
//what = null;
                            }
                            break;
                        default:
                            System.out.println("Uncaught type = " + rsmd.getColumnType(i + 1) + " " + String.valueOf(rs.getString(i + 1)));
                            what = String.valueOf(rs.getString(i + 1));
                    }
                    if (i + 1 < numberOfColumns) {
                        line = line + what + ",";
                    } else {
                        line = line + what + ")";
                    }
                }
                if (js.getCreateInsertScript()) {
                    ScriptService.addToSQLScript(stmt2.toString().substring(stmt2.toString().lastIndexOf(':') + 1));
                }
                stmt2.addBatch();
                batching = batching + 1;
                try {
                    if (batching > this.getBATCH_SIZE()) {
                        stmt2.executeBatch();
                        int batching2 = batching - 1;
                        System.out.println(String.format("DML Statements in Batch = " + batching2 + " Last DML executed = " + stmt2.toString()));
                        conTo.commit();
                        batching = 1;
                    }
                } catch (Exception exc) {
                    System.out.println("Some error happened during batch execution. " + exc.getMessage());
                }
            }
            stmt2.executeBatch();
            int batching2 = batching - 1;
            System.out.println(String.format("DML Statements in Batch = " + batching2 + " Last DML executed = " + stmt2.toString()));
            conTo.commit();
            if (js.getCreateInsertScript()) {
                ScriptService.buildSQLScript(js.getScriptFile());
            }
        } catch (Exception e) {
            System.out.println("Some error happened. " + e.getMessage() + " " + stmt2.toString());
        }
        rs.close();
        stmt.close();
        stmt2.close();
        System.out.println("Leaving copyData " + each);
    }

    public static class ColDesc {
        private String column;
        private String description;

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static class TabDesc {
        private String table;
        private String description;

        public String getTable() {
            return table;
        }

        public void setTable(String table) {
            this.table = table;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
