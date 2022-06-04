package com.closa.replicator.services;
import com.closa.replicator.domain.RPRequest;
import com.closa.replicator.domain.WorkRequest;
import com.closa.replicator.functions.James;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Service
public class CloneTable {
@Autowired
UtilsService utilsService;
public void CloneTable(Connection conFrom, Connection conTo, RPRequest js, String each) throws SQLException {
WorkRequest rq = new MetaDataEngine().getMetadata(conFrom, js, each);
DatabaseMetaData metaData = conTo.getMetaData();
Statement stmt = conTo.createStatement();
if (js.getTargetSystem().equalsIgnoreCase("mysql")) {
String dropSql = "DROP TABLE IF EXISTS " + js.getTargetSchema() + "." + each;
stmt.execute(dropSql);
System.out.println("Dropped table " + each);
ScriptService.addToSQLScript(dropSql);
}
if (js.getTargetSystem().equalsIgnoreCase("oracle")) {
String dropSql = "DROP TABLE " + js.getTargetSchema() + "." + each;
try {
stmt.execute(dropSql);
} catch (SQLException e) {
}
System.out.println("Dropped table " + each);
ScriptService.addToSQLScript(dropSql);
}
/*
get the CREATE TABLE build, and execute it
*/
List<String> crtSQLs = utilsService.genDDL(rq);
String crtSQL = crtSQLs.get(0);
crtSQLs.remove(0);
rq.setSqlStmt(crtSQL); // put it in Object
String catalog = js.getTargetSchema();
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
if (js.getTargetSystem().equalsIgnoreCase("oracle")) {
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
/*
get the CREATE INDEX(es) statmeents and execute them
*/
if ((rq.getRequestWorkTable().getTableWorkIndices().size() > 0) ) {
List<String> existingIndexes = new ArrayList<>();
ResultSet indexes = metaData.getIndexInfo(catalog, null, table, false, false);
while (indexes.next()) {
existingIndexes.add(indexes.getString("INDEX_NAME"));
}
ArrayList<String> sqls = new ArrayList<String>();
//      rq.getJs().setTsetMsTable(table);
sqls = utilsService.alterSQL(rq, existingIndexes);
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
}
