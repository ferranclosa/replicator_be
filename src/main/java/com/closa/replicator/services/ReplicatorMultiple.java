package com.closa.replicator.services;
import com.closa.replicator.domain.WorkRequest;
import com.closa.replicator.domain.WorkTableDetails;
import com.closa.replicator.domain.views.ConnectionParams;
import com.closa.replicator.functions.Connector;
import com.closa.replicator.functions.CsvWriter;
import com.closa.replicator.functions.James;
import com.closa.replicator.functions.LoadProps;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
/**
* Simple query of PDM Options file, printing output to console
*/
public class ReplicatorMultiple {
/**
* command line control
*
* @param
* @throws SQLException
*/
/*  public static void theMain() throws SQLException, IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, URISyntaxException {
*//*
Variables
*//*
Connection conFrom = null;
Connection conTo = null;
*//*
James is the utility guy, the mayordomo
*//*
WorkRequest rq = new WorkRequest();
LoadProps lp = new LoadProps();
ConnectionParams js = lp.loadProps("HUB");
if(js == null ){
System.out.println("Incorrect set up. Check request.txt");
System.exit(0);
}
GregorianCalendar todayStart = new GregorianCalendar();
rq.setTodayStart(todayStart);
conFrom = new Connector().ConFrom(js);
*//**
* there are 6 patterns:
* 1. usageInfoTable. This provides information on column data usage at e table level
* 2. usageInfoTableList. Same as above but based on a provided list
* 3. cloneEmptyTable. This will, based on source metadata, reconstruct same table on target system.
* 4. cloneEmptyTableList. Same as above but based on a provided list of files
* 5.
*//*
//        switch (js.getRequestPattern()) {
//
//            case "usageInfoTable":
//                usageInfoTable(conFrom, js);
//                break;
//            case "usageInfoTableList":
//                usageInfoTableList(conFrom, js);
//                break;
//            case "cloneEmptyTable":
//                conTo = new Connector().ConTo(js);
//                recreateEmptyTable(conFrom, conTo, js);
//                break;
//            case "cloneEmptyTableList":
//                conTo = new Connector().ConTo(js);
//                recreateEmptyTableList(conFrom, conTo, js);
//                break;
//            case "cloneTableWithData":
//                conTo = new Connector().ConTo(js);
//                recreateEmptyTable(conFrom, conTo, js);
//                js.copyData(conFrom, conTo, js);
//                break;
//            case "cloneTableListWithData":
//                conTo = new Connector().ConTo(js);
//                recreateEmptyTableList(conFrom, conTo, js);
//                copyDataTableList(conFrom, conTo, js);
//                break;
//            case "cloneTableWithDataWithoutWaste":
//                conTo = new Connector().ConTo(js);
//                recreateEmptyTableList(conFrom, conTo, js);
//                copyDataTableList(conFrom, conTo, js);
//                removeWasteonTable(conTo, js);
//                break;
//            case "cloneTableListWithDataWithoutWaste":
//                conTo = new Connector().ConTo(js);
//                recreateEmptyTableList(conFrom, conTo, js);
//                copyDataTableList(conFrom, conTo, js);
//                removeWasteOnList(conTo, js);
//                break;
//            case "copyTableListOnlyData":
//                conTo = new Connector().ConTo(js);
//                copyDataTableList(conFrom, conTo, js);
//                break;
//            case "reportTableStruct":
//                reportTableStruct(conFrom, js);
//                break;
//            case "reportTableListStruct":
//                reportTableListStruct(conFrom, js);
//                break;
//            default:
//                throw new NoSuchMethodError("Pattern " + js.getRequestPattern() + " not yet supported");
//        }
}
private static void basedOnListFour(Connection conFrom, Connection conTo, James js) throws SQLException {
Statement statement = conTo.createStatement();
if (js.getUseListofFiles()) {
js.setFileWithList(js.getFileWithList());
List<String> listOfFiles = js.readListofFiles(js.getFileWithList());
for (String each : listOfFiles) {
// Create a query to use.
String query = "SELECT * FROM " + each;
// Execute the query and get the result set, which contains
// all the results returned from the database.
ResultSet resultSet = statement.executeQuery(query);
String rmvSQL = js.crtWasteSQL(resultSet, each);
js.rmvWasteSQL(conTo, rmvSQL, each);
}
}
}
private static List<WorkTableDetails> reportTableStruct(Connection conFrom, James js) throws SQLException, IOException, URISyntaxException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
WorkRequest rq = new MetaDataEngine().getMetadata(conFrom, js, js.getSourceTableName());
String csvFileName = js.getSourceTableName();
final String[] header = {
"Schema", "" +
"Table_Name",
"Table_Comment",
"Columns_in_Table",
"Indexes_In_Table",
"Primary_KEY",
"Column_Name",
"Column_Comment",
"Data_Type",
"Maximum_Length",
"Decimal_Scale",
"Column_Index",
"Nullable",
"Default_Value",
"Sample_Data"};
CsvWriter.writeCSVFileHeader(csvFileName, header);
Statement statement = conFrom.createStatement();
String query = "SELECT * FROM " + js.getSourceTableName();
ResultSet resultSet = statement.executeQuery(query);
Map<String, String> defaultValues = js.findFieldDefaults(resultSet, conFrom, js.getSourceTableName());
Map<String, String> sampleValues = js.findFieldSample(resultSet, conFrom, js.getSourceTableName());
List<WorkTableDetails> listTD = js.genStructToFile(rq, defaultValues, sampleValues, js);
CsvWriter.writeCSVFileLine(csvFileName, listTD);
return listTD;
}
private static List<WorkTableDetails> reportTableStruct(Connection conFrom, James js, String table) throws SQLException, IOException, URISyntaxException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
System.out.println("Entering reportTableStruct with " + table);
WorkRequest rq = new MetaDataEngine().getMetadata(conFrom, js, table);
Statement statement = conFrom.createStatement();
String query = "SELECT * FROM " + table;
ResultSet resultSet = statement.executeQuery(query);
Map<String, String> defaultValues = js.findFieldDefaults(resultSet, conFrom,table);
Map<String, String> sampleValues = js.findFieldSample(resultSet, conFrom, table);
List<WorkTableDetails> listTD = js.genStructToFile(rq, defaultValues, sampleValues, js);
System.out.println("Exit reportTableStruct with " + table);
return listTD;
}
private static void reportTableListStruct(Connection conFrom, James js) throws SQLException, IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, URISyntaxException {
List<WorkTableDetails> listTD = new ArrayList<>();
String csvFileName = null;
if (js.getUseListofFiles()) {
if (!js.getReportCSVOut().isEmpty()) {
csvFileName = js.getReportCSVOut();
final String[] header = {
"Schema", "" +
"Table_Name",
"Table_Comment",
"Columns_in_Table",
"Indexes_In_Table",
"Primary_KEY",
"Column_Name",
"Column_Comment",
"Data_Type",
"Maximum_Length",
"Decimal_Scale",
"Column_Index",
"Nullable",
"Default_Value",
"Sample_Data"};
CsvWriter.writeCSVFileHeader(csvFileName, header);
}
js.setFileWithList(js.getFileWithList());
List<String> listOfFiles = js.readListofFiles(js.getFileWithList());
for (String each : listOfFiles) {
listTD.clear();
listTD.addAll(reportTableStruct(conFrom, js, each));
CsvWriter.writeCSVFileLine(csvFileName, listTD);
}
}
}
private static void copyDataTableList(Connection conFrom, Connection conTo, James js) throws SQLException {
if (js.getUseListofFiles()) {
js.setFileWithList(js.getFileWithList());
List<String> listOfFiles = js.readListofFiles(js.getFileWithList());
for (String each : listOfFiles) {
js.copyData(conFrom, conTo, js, each);
ScriptService.buildSQLScript(js.getScriptFile());
}
}
}
private static void recreateEmptyTableList(Connection conFrom, Connection conTo, James js) throws SQLException {
if (js.getUseListofFiles()) {
js.setFileWithList(js.getFileWithList());
List<String> listOfFiles = js.readListofFiles(js.getFileWithList());
for (String each : listOfFiles) {
js.setMsTable(each);
recreateEmptyTable(conFrom, conTo, js, each);
ScriptService.buildSQLScript(js.getScriptFile());
}
}
}
private static void removeWasteOnList(Connection conTo, James js) throws SQLException {
if (js.getUseListofFiles()) {
js.setFileWithList(js.getFileWithList());
List<String> listOfFiles = js.readListofFiles(js.getFileWithList());
for (String each : listOfFiles) {
// Create the statement to be used to get the results.
Statement statement = conTo.createStatement();
// Create a query to use.
String query = "SELECT * FROM " + each;
// Execute the query and get the result set, which contains
// all the results returned from the database.
ResultSet resultSet = statement.executeQuery(query);
String rmvSQL = js.crtWasteSQL(resultSet, each);
js.rmvWasteSQL(conTo, rmvSQL, each);
}
}
}
private static void removeWasteonTable(Connection conTo, James js) throws SQLException {
Statement statement = conTo.createStatement();
// Create a query to use.
String query = "SELECT * FROM " + js.getMsTable();
// Execute the query and get the result set, which contains
// all the results returned from the database.
ResultSet resultSet = statement.executeQuery(query);
String rmvSQL = js.crtWasteSQL(resultSet, js.getMsTable());
js.rmvWasteSQL(conTo, rmvSQL, js.getMsTable());
}
private static void recreateEmptyTable(Connection conFrom, Connection conTo, James js, String each) throws SQLException {
System.out.println("Entering recreateEmptyTable " + each);
WorkRequest rq = new MetaDataEngine().getMetadata(conFrom, js, each);
DatabaseMetaData metaData = conTo.getMetaData();
Statement stmt = conTo.createStatement();
if (js.getDropTableBefore()) {
if (js.getToDB().equalsIgnoreCase("mysql")) {
String dropSql = "DROP TABLE IF EXISTS " + js.getMsSchema() + "." + each;
stmt.execute(dropSql);
System.out.println("Dropped table " + each);
ScriptService.addToSQLScript(dropSql);
}
if (js.getToDB().equalsIgnoreCase("oracle")) {
String dropSql = "DROP TABLE " + js.getMsSchema() + "." + each;
try {
stmt.execute(dropSql);
} catch (SQLException e) {
}
System.out.println("Dropped table " + each);
ScriptService.addToSQLScript(dropSql);
}
}
*//*
get the CREATE TABLE build, and execute it
*//*
//String crtSQL = js.genDDL(rq).get(0);
List<String> crtSQLs = js.genDDL(rq);
String crtSQL = crtSQLs.get(0);
crtSQLs.remove(0);
rq.setSqlStmt(crtSQL); // put it in Object
String catalog = js.getMsSchema();
String table = each;
ResultSet rs1 = metaData.getTables(catalog, null, table, null);
if (rs1.next() == false) {
try {
stmt.executeUpdate(crtSQL); // do the Create WorkTable
ScriptService.addToSQLScript(crtSQL);
} catch (SQLException e) {
System.out.println("Error during CREATE with Statement = " + crtSQL);
e.printStackTrace();
}
if (js.getToDB().equalsIgnoreCase("oracle")) {
for (String one : crtSQLs) {
try {
stmt.executeUpdate(one); // do the Comments for Oracle
ScriptService.addToSQLScript(one);
} catch (SQLException e) {
System.out.println("Error during COMMENT ON  Statement = " + one);
e.printStackTrace();
}
}
}
System.out.println("DDL Statement = " + crtSQL);
}
*//*
get the CREATE INDEX(es) statmeents and execute them
*//*
if ((rq.getRequestWorkTable().getTableWorkIndices().size() > 0) && (js.getRecreateIndexes())) {
List<String> existingIndexes = new ArrayList<>();
ResultSet indexes = metaData.getIndexInfo(catalog, null, table, false, false);
while (indexes.next()) {
existingIndexes.add(indexes.getString("INDEX_NAME"));
}
ArrayList<String> sqls = new ArrayList<String>();
rq.getJs().setMsTable(table);
sqls = js.alterSQL(rq, existingIndexes);
rq.setSqlStmts(sqls);
for (int i = 0; i < sqls.size(); i++) {
String sql = sqls.get(i);
try {
stmt.executeUpdate(sql);
System.out.println("INDEX CREATED :" + sql);
ScriptService.addToSQLScript(sql);
stmt.close();
} catch (SQLException s) {
switch (s.getErrorCode()) {
case 1072:
System.out.println("Sql failed due to fields not existing in table: " + sql);
break;
case 1061:
break;
case 1064:
System.out.println("Check for unsupported characters)" + sql);
break;
default:
break;
}
}
}
}
rs1.close();
stmt.close();
System.out.println("Leaving recreateEmptyTable " + each);
}
*//**
* usageInfoTable just produces a report about the usage of the contents on a table
* This helps deduct default values. Essentially it lists all the columns on a table
* that have a single value.
*
* @param conFrom
* @param js
* @throws SQLException
*//*
private static void usageInfoTable(Connection conFrom, James js) throws SQLException {
Statement statement = conFrom.createStatement();
String query = "SELECT * FROM " + js.getSourceTableName();
ResultSet resultSet = statement.executeQuery(query);
js.findFieldsSameValue(resultSet, conFrom, js.getSourceTableName());
}
*//**
* usageInfoTableList does the same as usageInfoTable but based on a provided list of files
* (instead of doing one by one, you can provide a list and it will do then sequentially )
*
* @param conFrom
* @param js
* @throws SQLException
*//*
private static void usageInfoTableList(Connection conFrom, James js) throws SQLException {
if (js.getUseListofFiles()) {
js.setFileWithList(js.getFileWithList());
List<String> listOfFiles = js.readListofFiles(js.getFileWithList());
for (String each : listOfFiles) {
Statement statement = conFrom.createStatement();
String query = "SELECT * FROM " + each;
ResultSet resultSet = statement.executeQuery(query);
js.findFieldsSameValue(resultSet, conFrom, each);
}
}
}
*/
}
