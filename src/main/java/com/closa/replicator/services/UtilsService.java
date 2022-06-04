package com.closa.replicator.services;
import com.closa.replicator.domain.*;
import com.closa.replicator.functions.James;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class UtilsService {
private final Logger log = LoggerFactory.getLogger(this.getClass());
public void buildSourceData (){
}
/**
* This method takes responsaboility for building the SQL criteria part (JOIN, WHERE and LIMIT) based on the
* contents of the database.
*
* Its called from a loop that will pass the individual file and will return the SQL statement that can be added to a "SELECT * FROM <FILE> a"
*
* @param file (eeach file in the request fileList)
* @param targetSystem (this will be the target system, to respect differences on Syntax, for example)
* @param tempSchema (on remote scenarions, we use a temp schema to build the extrantion before tranfert)
* @return the CRITERIA part of the SQL (INNER JOIN, WHERE and LIMIT)
*/
public String buildExtractionCriteria(RPFile file, String targetSystem, String tempSchema) {
log.info("Entering buildExtractionCriteria " + file.getFileName());
String criteria = "";
String theJoinBlock = "";
String theWhereBlock = "";
String theLimitBlock = "";
String joinType = "";
String masterFile = "";
List<RPExtraction> thefileRules = file.getExtractionList();
for (RPExtraction one : thefileRules) {
String method = one.getExtMethod();
switch (method) {
case "ALL":
break;
case "MANY":
RPMethMANY instanceMany = (RPMethMANY) one;
joinType  = "" + instanceMany.getJoinType().toString() + " JOIN " + tempSchema + "." + instanceMany.getManyFile() + " B  ON " ;
if(!theJoinBlock.contains(joinType)){
theJoinBlock =  " " + joinType + " A." + instanceMany.getOneColumn() + " = B." + instanceMany.getManyColumn() ;
}
else{
theJoinBlock = theJoinBlock + " " + instanceMany.getLinkType().toString() + " A." +  instanceMany.getOneColumn() + " = B." + instanceMany.getManyColumn();
}
break;
case "WHERE":
RPMethWHERE instanceWhere = (RPMethWHERE) one;
if (!theWhereBlock.contains("WHERE")) {
theWhereBlock = theWhereBlock + " WHERE " + instanceWhere.getWhereStatement();
} else {
theWhereBlock = theWhereBlock + " AND " + instanceWhere.getWhereStatement();
}
break;
case "VALUES":
RPMethVALUES instanceValues = (RPMethVALUES) one;
String operator = null;
String value = null;
switch (instanceValues.getRelationship().toString()) {
case "EQUAL_TO":
operator = "=";
break;
case "NOT_EQUAL_TO":
operator = "<>";
break;
case "GREATER_THAN":
operator = ">";
break;
case "LESS_THAN":
operator = "<";
break;
default:
operator = "";
}
switch (instanceValues.getColumnType().toString()) {
case "STRING":
value = "'" + instanceValues.getColumnValue() + "'";
break;
default:
value = instanceValues.getColumnValue();
break;
}
if (!theWhereBlock.contains("WHERE")) {
theWhereBlock = theWhereBlock + " WHERE " + instanceValues.getColumnName()  + operator + value;
} else {
theWhereBlock = theWhereBlock + " AND " + instanceValues.getColumnName()  + operator + value;
}
break;
case "LIMIT":
RPMethLIMIT instanceLimit = (RPMethLIMIT) one;
if(targetSystem.equalsIgnoreCase("mysql")) {
theLimitBlock = " LIMIT " + instanceLimit.getLimitBy();
}
if(targetSystem.equalsIgnoreCase("oracle")) {
theLimitBlock = " FETCH NEXT  " + instanceLimit.getLimitBy() + " ROWS";
}
break;
default:
criteria = "";
}
}
criteria = theJoinBlock+ " " + theWhereBlock + " " + theLimitBlock;
log.info("Leaving buildExtractionCriteria " + file.getFileName());
return criteria;
}
public void copyData(Connection conFrom, Connection conTo, WorkRequest js, String each) throws SQLException {
Statement stmt = conTo.createStatement();
log.info("Entering copyData " + each);
if (js.getJs().getTargetClearBefore()) {
String sql = "delete  from " + js.getJs().getTargetSchema() + "." + each;
sql = sql.replace("@", "_");
stmt.execute(sql);
log.info("Cleared contents from table " + each);
}
String sql = "select * from " + js.getJs().getSourceTempSchema() + "." + each;
PreparedStatement queryAll = conFrom.prepareStatement(sql);
ResultSet rs = queryAll.executeQuery();
ResultSetMetaData rsmd = rs.getMetaData();
int numberOfColumns = rsmd.getColumnCount();
String lineValues = ") VALUES (";
String lineInsert = "INSERT INTO  " + js.getJs().getTargetSchema().toLowerCase() + "." + each + " ( ";
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
log.info("Writting Results File..." + each);
conTo.setAutoCommit(false);
while (rs.next()) {
String what = " ";
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
}
break;
default:
log.info("Uncaught type = " + rsmd.getColumnType(i + 1) + " " + String.valueOf(rs.getString(i + 1)));
what = String.valueOf(rs.getString(i + 1));
}
if (i + 1 < numberOfColumns) {
line = line + what + ",";
} else {
line = line + what + ")";
}
}
stmt2.addBatch();
batching = batching + 1;
try {
if (batching > js.getJs().getBatchSize()) {
stmt2.executeBatch();
int batching2 = batching - 1;
log.info(String.format("DML Statements in Batch = " + batching2 + " Last DML executed = " + stmt2.toString()));
conTo.commit();
batching = 1;
}
} catch (Exception exc) {
log.info("Some error happened during batch execution. " + exc.getMessage());
}
}
stmt2.executeBatch();
int batching2 = batching - 1;
log.info(String.format("DML Statements in Batch = " + batching2 + " Last DML executed = " + stmt2.toString()));
conTo.commit();
} catch (Exception e) {
log.info("Some error happened. " + e.getMessage() + " " + stmt2.toString());
}
lineInsert = "";
rs.close();
stmt.close();
stmt2.close();
log.info("Leaving copyData " + each);
}
public List<James.ColDesc> getAS400ColumnDescriptions(Connection conFrom, String theSchema, String theTable) throws SQLException {
List<James.ColDesc> colDescs = new ArrayList<>();
String theSQL = "Select  COLUMN_NAME,   " +
"varchar(COLUMN_TEXT, 50) From QSYS2.SYSCOLUMNS  " +
"where table_name = '" + theTable.toUpperCase() +
"' and table_schema = '" + theSchema.toUpperCase() + "'";
PreparedStatement queryAll = conFrom.prepareStatement(theSQL);
ResultSet rs = queryAll.executeQuery();
while (rs.next()) {
String x = rs.getString(2);
if (x != null) {
James.ColDesc colDesc = new James.ColDesc();
x = x.replace("'", " ");
colDesc.setColumn(rs.getString(1));
colDesc.setDescription(x);
colDescs.add(colDesc);
}
}
rs.close();
return colDescs;
}
public List<James.TabDesc> getAS400FileDescriptions(Connection conFrom, String theSchema, String theTable) throws SQLException {
List<James.TabDesc> tabDescs = new ArrayList<>();
String theSQL = "Select  TABLE_NAME," +
"varchar(TABLE_TEXT, 50) As COLUMN_DESC " +
"From QSYS2.SYSTables  " +
"where table_name = '" + theTable.toUpperCase() + "'" +
"and table_schema = '" + theSchema.toUpperCase() + "'";
PreparedStatement queryAll = conFrom.prepareStatement(theSQL);
ResultSet rs = queryAll.executeQuery();
while (rs.next()) {
James.TabDesc tabDesc = new James.TabDesc();
tabDesc.setTable(rs.getString(1));
tabDesc.setDescription(rs.getString(2));
tabDescs.add(tabDesc);
}
rs.close();
return tabDescs;
}
public List<String> genDDL(WorkRequest rq) throws SQLException {
List<String> listOfStatements = new ArrayList<>();
if (rq.getJs().getTargetSystem().equalsIgnoreCase("oracle")) {
return genOracleDDL(rq);
}
String sql = null;
String pkString = null;
String comments = " COMMENT  '";
try {
if (rq.getJs().getTargetDropBefore()) {
if (rq.getJs().getTargetSystem().equalsIgnoreCase("mysql")) {
String dropSql = "DROP TABLE IF EXISTS " + rq.getJs().getTargetSchema() + "." + rq.getRequestWorkTable().getTableName();
dropSql = dropSql.replace("@", "_");
listOfStatements.add(0, dropSql);
}
if (rq.getJs().getTargetSystem().equalsIgnoreCase("oracle")) {
String dropSql = "DROP TABLE " + rq.getJs().getTargetSchema() + "." + rq.getRequestWorkTable().getTableName();
dropSql = dropSql.replace("@", "_");
listOfStatements.add(0, dropSql);
}
}
sql = "CREATE TABLE " + rq.getJs().getTargetSchema() + "." + rq.getRequestWorkTable().getTableName() + " ( ";
ArrayList<WorkColumn> cl0 = new ArrayList<WorkColumn>();
cl0.addAll(rq.getRequestWorkTable().getTableWorkColumns());
Map map = new HashMap();
for (James.ColDesc each : rq.getColDescs()) {
map.put(each.getColumn(), each.getDescription());
}
Map map2 = new HashMap();
for (James.TabDesc each : rq.getTabDescs()) {
map2.put(each.getTable(), each.getDescription());
}
WorkColumn cl = new WorkColumn();
for (int i = 0; i < rq.getRequestWorkTable().getTableWorkColumns().size(); i++) {
String type = null;
cl = cl0.get(i);
if (cl.getColumnLabel() == null) {
cl.setColumnLabel(String.valueOf(map.get(cl.getColumnName())));
}
switch (cl.getColumnSQLDtaType()) {
case 1: // String
if (cl.getColumnPrecision() > 254) { //if char but > 254 convert to VARCHAR
type = cl.getColumnName()
+ " VARCHAR  ("
+ cl.getColumnPrecision() + ")" +
comments + cl.getColumnLabel() + "'";
} else {
type = cl.getColumnName() + " "
+ cl.getColumnType() + " ("
+ cl.getColumnPrecision() + ")" +
comments + cl.getColumnLabel() + "'";
}
break;
case 3: // decimal with scale
case 8:
case 6:
case 2:
case 7:
type = cl.getColumnName() + " "
+ cl.getColumnType() + " ("
+ cl.getColumnPrecision() + "," + cl.getColumnScale() + ")" +
comments + cl.getColumnLabel() + "'";
break;
case -5: // decimal with precision and no scale
case -7:
case -6:
type = cl.getColumnName() + " "
+ cl.getColumnType() + " ("
+ cl.getColumnPrecision() + ")" +
comments + cl.getColumnLabel() + "'";
break;
// for timestanps , dates, etc, no precision
case 93:
case 91:
case 92:
type = cl.getColumnName() + " "
+ cl.getColumnType();
break;
case 12: // for VARCHAR if precision 0 ignore precision
String typeVar = "VARCHAR";
if (cl.getColumnPrecision() == 0) {
type = cl.getColumnName() + " "
+ typeVar + " )";
} else {
type = cl.getColumnName() + " "
+ typeVar + " ("
+ cl.getColumnPrecision() + ")";
}
break;
default:
type = cl.getColumnName() + " "
+ cl.getColumnType() + " ("
+ cl.getColumnPrecision() + "," + cl.getColumnScale()
+ ")  ";
}
if (cl.getIsNullable() == "YES") {
type = type + " NULL ";
} else {
type = type + "  NOT NULL ";
}
if (i + 1 < rq.getRequestWorkTable().getTableWorkColumns().size()) {
sql = sql + type + ", ";
} else {
ArrayList<WorkIndex> ix0 = new ArrayList<>();
int x = 1;
ix0 = (ArrayList<WorkIndex>) rq.getRequestWorkTable().getTableWorkIndices().stream()
.filter(one -> one.getIndexName().equalsIgnoreCase(rq.getRequestWorkTable().getTableName()))
.filter(one -> one.getNonUnique().booleanValue() == false)
.collect(Collectors.toList());
if (ix0.isEmpty()) {
pkString = type;
} else {
pkString = type + ",   PRIMARY KEY (";
for (WorkIndex one : ix0) {
if ((!one.getNonUnique()) && (one.getIndexName().equalsIgnoreCase(rq.getRequestWorkTable().getTableName()))) {
pkString = pkString + one.getColumnName();
if (x < ix0.size()) {
pkString = pkString + ",";
}
x++;
}
}
pkString = pkString + ")";
}
//  }
}
}
sql = sql + pkString + ") ";
sql = sql.replace("#", "_");
sql = sql.replace("@", "_");
listOfStatements.add(1, sql);
return listOfStatements;
} catch (Exception e) {
throw new SQLException(e.getMessage());
}
}
public List<String> genOracleDDL(WorkRequest rq) throws SQLException {
List<String> listOfStatements = new ArrayList<>();
String sql = null;
String pkString = null;
try {
sql = "CREATE TABLE " + rq.getJs().getTargetSchema() + "." + rq.getRequestWorkTable().getTableName() + " ( ";
ArrayList<WorkColumn> cl0 = new ArrayList<WorkColumn>();
cl0.addAll(rq.getRequestWorkTable().getTableWorkColumns());
Map map = new HashMap();
for (James.ColDesc each : rq.getColDescs()) {
map.put(each.getColumn(), each.getDescription());
}
Map map2 = new HashMap();
for (James.TabDesc each : rq.getTabDescs()) {
map2.put(each.getTable(), each.getDescription());
}
WorkColumn cl = new WorkColumn();
for (int i = 0; i < rq.getRequestWorkTable().getTableWorkColumns().size(); i++) {
String type = null;
cl = cl0.get(i);
if (cl.getColumnLabel() == null) {
cl.setColumnLabel(String.valueOf(map.get(cl.getColumnName())));
}
switch (cl.getColumnSQLDtaType()) {
case 1: // String
if (rq.getJs().getTargetSystem().equalsIgnoreCase("oracle")) {
type = cl.getColumnName() +
" VARCHAR2 (" + cl.getColumnPrecision() + " CHAR ) ";
} else {
if (cl.getColumnPrecision() > 254) { //if char but > 254 convert to VARCHAR
type = cl.getColumnName()
+ " VARCHAR  ("
+ cl.getColumnPrecision() + ")";
} else {
type = cl.getColumnName() + " "
+ cl.getColumnType() + " ("
+ cl.getColumnPrecision() + ")"
;
}
}
break;
case 3: // decimal with scale
case 8:
case 6:
case 2:
case 7:
type = cl.getColumnName() + " "
+ cl.getColumnType() + " ("
+ cl.getColumnPrecision() + "," + cl.getColumnScale() + ")";
break;
case -5: // decimal with precision and no scale
case -7:
case -6:
type = cl.getColumnName() + " "
+ cl.getColumnType() + " ("
+ cl.getColumnPrecision() + ")";
break;
// for timestanps , dates, etc, no precision
case 93:
case 91:
case 92:
type = cl.getColumnName() + " "
+ cl.getColumnType();
break;
case 12: // for VARCHAR if precision 0 ignore precision
String typeVar2 = "VARCHAR2";
String typeVar = "VARCHAR";
if (cl.getColumnPrecision() == 0) {
type = cl.getColumnName() + " "
+ typeVar2 + " )";
} else {
type = cl.getColumnName() + " "
+ typeVar2 + " ("
+ cl.getColumnPrecision() + ")";
}
break;
default:
type = cl.getColumnName() + " "
+ cl.getColumnType() + " ("
+ cl.getColumnPrecision() + "," + cl.getColumnScale()
+ ")  ";
}
if (cl.getIsNullable() == "YES") {
type = type + " NULL ";
} else {
type = type + "  NOT NULL ";
}
if (i + 1 < rq.getRequestWorkTable().getTableWorkColumns().size()) {
sql = sql + type + ", ";
} else {
if (rq.getRequestWorkTable().getTableWorkIndices().size() > 0) {
ArrayList<WorkIndex> ix0 = new ArrayList<>();
int x = 1;
ix0 = (ArrayList<WorkIndex>) rq.getRequestWorkTable().getTableWorkIndices().stream()
.filter(one -> one.getIndexName().equalsIgnoreCase(rq.getRequestWorkTable().getTableName()))
.filter(one -> one.getNonUnique().booleanValue() == false)
.collect(Collectors.toList());
if (ix0.isEmpty()) {
pkString = type;
} else {
pkString = type + ",   PRIMARY KEY (";
for (WorkIndex one : ix0) {
if ((!one.getNonUnique()) && (one.getIndexName().equalsIgnoreCase(rq.getRequestWorkTable().getTableName()))) {
pkString = pkString + one.getColumnName();
if (x < ix0.size()) {
pkString = pkString + ",";
}
x++;
}
}
pkString = pkString + ")";
}
}
}
}
String commentsStr = null;
Iterator it = map.entrySet().iterator();
while (it.hasNext()) {
Map.Entry one = (Map.Entry) it.next();
commentsStr = " COMMENT ON COLUMN " + rq.getRequestWorkTable().getTableName() + "." + one.getKey() + " IS '" + one.getValue() + "'";
commentsStr = commentsStr.replace("#", "_");
commentsStr = commentsStr.replace("@", "_");
listOfStatements.add(commentsStr);
it.remove();
}
it = map2.entrySet().iterator();
while (it.hasNext()) {
Map.Entry one = (Map.Entry) it.next();
commentsStr = " COMMENT ON TABLE " + one.getKey() + " IS '" + one.getValue() + "'";
commentsStr = commentsStr.replace("#", "_");
commentsStr = commentsStr.replace("@", "_");
listOfStatements.add(commentsStr);
it.remove();
}
sql = sql + pkString + ") ";
sql = sql.replace("#", "_");
sql = sql.replace("@", "_");
listOfStatements.add(0, sql);
return listOfStatements;
} catch (Exception e) {
throw new SQLException(e.getMessage());
}
}
public List<WorkTableDetails> genStructToFile(WorkRequest rq, Map<String, String> defaultValues, Map<String, String> sampleValues, James js) throws SQLException {
log.info("Building WorkTable Details for reporting  with " + rq.getRequestWorkTable().getTableName());
WorkTableDetails workTableDetails = new WorkTableDetails();
WorkTableDetails oldDetails = new WorkTableDetails();
List<WorkTableDetails> listOfWorkTableDetails = new ArrayList<>();
String pkString = null;
String csvFileName = null;
Map mapOfColumnLabels = new HashMap();
for (James.ColDesc each : rq.getColDescs()) {
mapOfColumnLabels.put(each.getColumn().toUpperCase(), each.getDescription());
}
Map mapOfTableLabels = new HashMap();
for (James.TabDesc each : rq.getTabDescs()) {
mapOfTableLabels.put(each.getTable().toUpperCase(), each.getDescription());
}
Map mapOfPrimaryKeys = new HashMap();
workTableDetails.setTdTableSchema(rq.getJs().getSourceSchema());
workTableDetails.setTdTableName(rq.getRequestWorkTable().getTableName());
workTableDetails.setTdTableColumns(rq.getRequestWorkTable().getTableWorkColumns().size());
workTableDetails.setTdTableIndexes(rq.getRequestWorkTable().getTableWorkIndices().size());
if (mapOfTableLabels.containsKey(workTableDetails.getTdTableName().toUpperCase())) {
workTableDetails.setTdTableComment((String) mapOfTableLabels.get(workTableDetails.getTdTableName().toUpperCase()));
} else {
workTableDetails.setTdTableComment(" ");
}
String tableComment = workTableDetails.getTdTableComment();
if (rq.getRequestWorkTable().getTableWorkIndices().size() > 0) {
ArrayList<WorkIndex> ix0 = new ArrayList<>();
int x = 1;
ix0 = (ArrayList<WorkIndex>) rq.getRequestWorkTable().getTableWorkIndices().stream()
.filter(one -> one.getIndexName().equalsIgnoreCase(rq.getRequestWorkTable().getTableName()))
.filter(one -> one.getNonUnique().booleanValue() == false)
.collect(Collectors.toList());
if (ix0.isEmpty()) {
pkString = "";
} else {
pkString = "";
for (WorkIndex one : ix0) {
if ((!one.getNonUnique()) && (one.getIndexName().equalsIgnoreCase(rq.getRequestWorkTable().getTableName()))) {
pkString = pkString + one.getColumnName();
if (x < ix0.size()) {
pkString = pkString + ":";
mapOfPrimaryKeys.put(one.getColumnName(), "PK");
}
x++;
}
}
if (pkString.isEmpty()) {
//   workTableDetails.setTdTablePrimaryKey(" ");
} else {
//   workTableDetails.setTdTablePrimaryKey(pkString);
}
}
}
String primaryKey = workTableDetails.getTdTablePrimaryKey();
oldDetails = workTableDetails;
try {
ArrayList<WorkColumn> cl0 = new ArrayList<WorkColumn>();
cl0.addAll(rq.getRequestWorkTable().getTableWorkColumns());
WorkColumn cl;
for (int i = 0; i < rq.getRequestWorkTable().getTableWorkColumns().size(); i++) {
cl = cl0.get(i);
if (mapOfPrimaryKeys.containsKey(cl.getColumnName())) {
workTableDetails.setTdTablePrimaryKey((String) mapOfPrimaryKeys.get(cl.getColumnName()));
} else {
workTableDetails.setTdTablePrimaryKey(" ");
}
workTableDetails.setTdTablePrimaryKey((String) mapOfPrimaryKeys.get(cl.getColumnName()));
workTableDetails.setTdColumnIndex(i + 1);
workTableDetails.setTdColumnName(cl.getColumnName());
workTableDetails.setTdColumnIndex(i + 1);
if (mapOfColumnLabels.containsKey(workTableDetails.getTdColumnName())) {
workTableDetails.setTdColumnComment((String) mapOfColumnLabels.get(workTableDetails.getTdColumnName()));
} else {
workTableDetails.setTdColumnComment("NOT_LABEL");
}
workTableDetails.setTdColumnType(cl.getColumnType());
workTableDetails.setTdColumnLength(cl.getColumnPrecision());
workTableDetails.setTdColumnPrecision(cl.getColumnScale());
if (cl.getIsNullable() == "YES") {
workTableDetails.setTdColumnNullable(true);
} else {
workTableDetails.setTdColumnNullable(false);
}
if (defaultValues.containsKey(workTableDetails.getTdColumnName())) {
workTableDetails.setTdColumnDefault(defaultValues.get(workTableDetails.getTdColumnName()));
} else {
workTableDetails.setTdColumnDefault("NOT_DEFAULT");
}
if (sampleValues.containsKey(workTableDetails.getTdColumnName())) {
workTableDetails.setTdColumnSampleValue(sampleValues.get(workTableDetails.getTdColumnName()));
} else {
workTableDetails.setTdColumnSampleValue("NOT DATA");
}
listOfWorkTableDetails.add(workTableDetails);
workTableDetails = new WorkTableDetails();
workTableDetails.setTdTableSchema(oldDetails.getTdTableSchema());
workTableDetails.setTdTableName(oldDetails.getTdTableName());
workTableDetails.setTdTableColumns(oldDetails.getTdTableColumns());
workTableDetails.setTdTableIndexes(oldDetails.getTdTableIndexes());
workTableDetails.setTdTableComment(oldDetails.getTdTableComment());
workTableDetails.setTdTablePrimaryKey(oldDetails.getTdTablePrimaryKey());
}
} catch (Exception e) {
throw new SQLException(e.getMessage());
}
log.info("Exit Building WorkTable Details for reporting  with " + rq.getRequestWorkTable().getTableName());
return listOfWorkTableDetails;
}
/*
Pass it a WorkRequest Object and it will return a Arrays of SQL to generate all indexes, including the PRIMARY KEY one
*/
public ArrayList<String> alterSQL(WorkRequest rq, List<String> indexes) {
ArrayList<String> sqls = new ArrayList<>();
if (rq.getRequestWorkTable().getTableWorkIndices().size() > 0) {
String sql = null;
ArrayList<WorkIndex> ix0 = new ArrayList<>();
ix0.addAll(rq.getRequestWorkTable().getTableWorkIndices());
WorkIndex ix = new WorkIndex();
Boolean odxFlag = false; //outer loop
Boolean idxFlag = false; // inner loop
ix = ix0.get(0); // read next
String idxName = "";
for (int i = 0; i < rq.getRequestWorkTable().getTableWorkIndices().size(); i++) {
String indexName = ix.getIndexName();
if (indexes.contains(indexName)) {
//       ix = ix0.get(i + 1);
ix = ix0.get(i);
continue;
}
if (!idxName.equalsIgnoreCase(indexName)) {
if (odxFlag) {
sql = sql + ")";
sql = sql.replace("#", "_");
sql = sql.replace("@", "_");
sqls.add(sql);
}
idxFlag = false;
if (ix.getNonUnique()) {
sql = "CREATE INDEX " + ix.getIndexName() + " ON "
+ rq.getJs().getTargetSchema() + "." + rq.getRequestWorkTable().getTableName() + " (";
}
if (!ix.getNonUnique()) {
sql = "CREATE UNIQUE INDEX " + ix.getIndexName() + " ON "
+ rq.getJs().getTargetSchema() + "." + rq.getRequestWorkTable().getTableName() + " (";
}
}
if (idxFlag) {
sql = sql + ",";
}
sql = sql + ix.getColumnName();
if ("A".equals(ix.getDescASC())) {
sql = sql + " ASC ";
} else {
sql = sql + " DESC ";
}
idxFlag = true;
odxFlag = true;
idxName = ix.getIndexName();
if (i + 1 < rq.getRequestWorkTable().getTableWorkIndices().size()) {
ix = ix0.get(i + 1); // read next
}
}
}
return sqls;
}
}
