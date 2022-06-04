package com.closa.replicator.services;
import com.closa.replicator.domain.*;
import com.closa.replicator.functions.James;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
@Component
public class MetaDataEngine {
@Autowired
UtilsService utlsService;
public MetaDataEngine() {
}
/*  *//**
* This method receives a connection and a table and it returns a WorkRequest, an dobject
* with the WorkTable and WorkIndex data that will allow for the table to be reproduced on the
* target system
* @param wConnection
* @param js
* @return a WorkRequest Object
* @throws SQLException
*//*
public WorkRequest getMetadata(Connection wConnection, James js, String each) throws SQLException {
DatabaseMetaData dbmd = wConnection.getMetaData();
WorkRequest rq = new WorkRequest();
WorkTable tb = new WorkTable();
if (js.getFromDB().equalsIgnoreCase("as400")){
rq.setColDescs(js.getAS400ColumnDescriptions(wConnection, js.getSourceSchemaName(), each));
rq.setTabDescs((js.getAS400FileDescriptions(wConnection, js.getSourceSchemaName(), each)));
}
ResultSet rsTable = dbmd.getTables(null, js.getSourceSchemaName(), each, null);
if(rsTable.next()){
tb.setTableSchema(js.getSourceSchemaName());
tb.setTableName(each);
tb.setTableLabels(rsTable.getString("REMARKS"));
tb.setTableType(rsTable.getString("TABLE_TYPE"));
}
*//*
Add the WorkTable to the root of the XML document
*//*
ResultSet rsIndex = dbmd.getIndexInfo(null, js.getSourceSchemaName(), each, false, false);
ArrayList<WorkIndex> ti = new ArrayList<WorkIndex>();
String ixName;
int idx = 0;
Boolean more = rsIndex.next();
while (more) {
ixName = rsIndex.getString("COLUMN_NAME");
if (ixName == null) {
break;
};
WorkIndex ix = new WorkIndex();
ix.setColumnName(rsIndex.getString("COLUMN_NAME"));
ix.setIndexName(rsIndex.getString("INDEX_NAME"));
ix.setNonUnique(rsIndex.getBoolean("NON_UNIQUE"));
ix.setIndexType(rsIndex.getShort("TYPE"));
ix.setOrdinalPos(rsIndex.getShort("ORDINAL_POSITION"));
ix.setDescASC(rsIndex.getString("ASC_OR_DESC"));
ix.setFilterCondition(rsIndex.getString("FILTER_CONDITION"));
ti.add(idx, ix);
more = rsIndex.next();
idx++;
}
tb.setTableWorkIndices(ti);
ResultSet rsColumn = dbmd.getColumns(null, js.getSourceSchemaName(), each, null);
ArrayList<WorkColumn> tc = new ArrayList<WorkColumn>();
idx = 0;
more = rsColumn.next();
while (more) {
WorkColumn cl = new WorkColumn();
cl.setColumnName(rsColumn.getString("COLUMN_NAME"));
cl.setColumnType(rsColumn.getString("TYPE_NAME"));
cl.setColumnSQLDtaType(rsColumn.getInt("DATA_TYPE"));
cl.setColumnPrecision(rsColumn.getInt("COLUMN_SIZE"));
cl.setColumnScale(rsColumn.getInt("DECIMAL_DIGITS"));
cl.setColumnLabel(rsColumn.getString("REMARKS"));
tc.add(idx, cl);
more = rsColumn.next();
idx++;
}
tb.setTableWorkColumns(tc);
rq.setJs(js);
rq.setRequestWorkTable(tb);
GregorianCalendar todayEnd = new GregorianCalendar();
rq.setTodayEnd(todayEnd);
rsColumn.close();
rsIndex.close();
rsTable.close();
return rq;
}*/
public WorkRequest getMetadata(Connection wConnection, RPRequest js, String each) throws SQLException {
DatabaseMetaData dbmd = wConnection.getMetaData();
WorkRequest rq = new WorkRequest();
WorkTable tb = new WorkTable();
if (js.getSourceSystem().equalsIgnoreCase("as400")){
rq.setColDescs(utlsService.getAS400ColumnDescriptions(wConnection, js.getSourceSchema(), each));
rq.setTabDescs((utlsService.getAS400FileDescriptions(wConnection, js.getSourceSchema(), each)));
}
ResultSet rsTable = dbmd.getTables(null, js.getSourceSchema(), each, null);
if(rsTable.next()){
tb.setTableSchema(js.getSourceSchema());
tb.setTableName(each);
tb.setTableLabels(rsTable.getString("REMARKS"));
tb.setTableType(rsTable.getString("TABLE_TYPE"));
}
/*
Add the WorkTable to the root of the XML document
*/
ResultSet rsIndex = dbmd.getIndexInfo(null, js.getSourceSchema(), each, false, false);
ArrayList<WorkIndex> ti = new ArrayList<WorkIndex>();
String ixName;
int idx = 0;
Boolean more = rsIndex.next();
while (more) {
ixName = rsIndex.getString("COLUMN_NAME");
if (ixName == null) {
break;
};
WorkIndex ix = new WorkIndex();
ix.setColumnName(rsIndex.getString("COLUMN_NAME"));
ix.setIndexName(rsIndex.getString("INDEX_NAME"));
ix.setNonUnique(rsIndex.getBoolean("NON_UNIQUE"));
ix.setIndexType(rsIndex.getShort("TYPE"));
ix.setOrdinalPos(rsIndex.getShort("ORDINAL_POSITION"));
ix.setDescASC(rsIndex.getString("ASC_OR_DESC"));
ix.setFilterCondition(rsIndex.getString("FILTER_CONDITION"));
ti.add(idx, ix);
more = rsIndex.next();
idx++;
}
tb.setTableWorkIndices(ti);
ResultSet rsColumn = dbmd.getColumns(null, js.getSourceSchema(), each, null);
ArrayList<WorkColumn> tc = new ArrayList<WorkColumn>();
idx = 0;
more = rsColumn.next();
while (more) {
WorkColumn cl = new WorkColumn();
cl.setColumnName(rsColumn.getString("COLUMN_NAME"));
cl.setColumnType(rsColumn.getString("TYPE_NAME"));
cl.setColumnSQLDtaType(rsColumn.getInt("DATA_TYPE"));
cl.setColumnPrecision(rsColumn.getInt("COLUMN_SIZE"));
cl.setColumnScale(rsColumn.getInt("DECIMAL_DIGITS"));
cl.setColumnLabel(rsColumn.getString("REMARKS"));
tc.add(idx, cl);
more = rsColumn.next();
idx++;
}
tb.setTableWorkColumns(tc);
rq.setJs(js);
rq.setRequestWorkTable(tb);
rsColumn.close();
rsIndex.close();
rsTable.close();
return rq;
}
}
