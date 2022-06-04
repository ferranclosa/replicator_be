package com.closa.replicator.services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
public class ScriptService {
private final Logger log = LoggerFactory.getLogger(ScriptService.class);
static List<String> listOfEvents = new ArrayList<>();
public static List<String> getListOfEvents() {
return listOfEvents;
}
public static void addToSQLScript(String sQLScript)  {
listOfEvents.add(sQLScript);
}
public static void buildSQLScript(String theTable) {
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
for (String eachOne : getListOfEvents()) {
line = eachOne + wsep;
outFileStream.println(line);
line = "";
}
outFileStream.flush();
outFileStream.close();
}
}
