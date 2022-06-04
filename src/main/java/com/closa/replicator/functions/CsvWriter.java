package com.closa.replicator.functions;

import com.closa.replicator.domain.WorkTableDetails;
import org.springframework.stereotype.Service;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * This program demonstrates how to write fields of POJOs to a CSV file
 * using SuperCSV library. Each JavaBean's fields are written to a row
 * in the CSV file.
 *
 * @author www.codejava.net
 */
@Service
public class CsvWriter {
    public static void writeCSVFile(String csvFileName, List<WorkTableDetails> listTables, Boolean multiple) {
        ICsvBeanWriter beanWriter = null;
// the name mapping provide the basis for bean setters
        final String[] nameMapping = new String[]{
                "tdTableSchema",
                "tdTableName",
                "tdTableComment",
                "tdTableColumns",
                "tdTableIndexes",
                "tdTablePrimaryKey",
                "tdColumnName",
                "tdColumnComment",
                "tdColumnType",
                "tdColumnLength",
                "tdColumnPrecision",
                "tdColumnIndex",
                "tdColumnNullable",
                "tdColumnDefault",
                "tdColumnSampleValue"
        };
        final CellProcessor[] processors = new CellProcessor[]{
                new Optional(), // Schema
                new NotNull(), // WorkTable
                new Optional(),  // columns
                new Optional(), // indexes
                new Optional(), // Comment
                new Optional(), // PK
                new NotNull(), // WorkColumn Name
                new Optional(), // WorkColumn index
                new Optional(), //comment
                new NotNull(), //Type
                new NotNull(), //length
                new Optional(), //scale
                new Optional(), // nullable
                new Optional(), //default
                new Optional() // sample data
        };
        try {
            beanWriter = new CsvBeanWriter(new FileWriter(csvFileName, true),
                    CsvPreference.STANDARD_PREFERENCE);
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
            beanWriter.writeHeader(header);
            for (WorkTableDetails aTable : listTables) {
                beanWriter.write(aTable, nameMapping, processors);
            }
        } catch (IOException ex) {
            System.err.println("Error writing the CSV file: " + ex);
        } finally {
            if (beanWriter != null) {
                try {
                    beanWriter.close();
                } catch (IOException ex) {
                    System.err.println("Error closing the writer: " + ex);
                }
            }
        }
    }

    public static void writeCSVFileHeaders(String csvFileName, List<WorkTableDetails> listTables) {
        ICsvBeanWriter beanWriter = null;
        try {
            beanWriter = new CsvBeanWriter(new FileWriter(csvFileName, false),
                    CsvPreference.STANDARD_PREFERENCE);
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
            beanWriter.writeHeader(header);
        } catch (IOException ex) {
            System.err.println("Error writing the CSV file: " + ex);
        } finally {
            if (beanWriter != null) {
                try {
                    beanWriter.close();
                } catch (IOException ex) {
                    System.err.println("Error closing the writer: " + ex);
                }
            }
        }
    }

    public static void writeCSVFileHeader(String csvFileName, final String[] header) {
        ICsvBeanWriter beanWriter = null;
        try {
            beanWriter = new CsvBeanWriter(new FileWriter(csvFileName, false),
                    CsvPreference.STANDARD_PREFERENCE);
            beanWriter.writeHeader(header);
        } catch (IOException ex) {
            System.err.println("Error writing the CSV file: " + ex);
        } finally {
            if (beanWriter != null) {
                try {
                    beanWriter.close();
                } catch (IOException ex) {
                    System.err.println("Error closing the writer: " + ex);
                }
            }
        }
    }

    public static void writeCSVFileLine(String csvFileName, List<WorkTableDetails> listTables) {
        ICsvBeanWriter beanWriter = null;
// the name mapping provide the basis for bean setters
        final String[] nameMapping = new String[]{
                "tdTableSchema",
                "tdTableName",
                "tdTableComment",
                "tdTableColumns",
                "tdTableIndexes",
                "tdTablePrimaryKey",
                "tdColumnName",
                "tdColumnComment",
                "tdColumnType",
                "tdColumnLength",
                "tdColumnPrecision",
                "tdColumnIndex",
                "tdColumnNullable",
                "tdColumnDefault",
                "tdColumnSampleValue"
        };
        final CellProcessor[] processors = new CellProcessor[]{
                new Optional(), // Schema
                new NotNull(), // WorkTable
                new Optional(),  // columns
                new Optional(), // indexes
                new Optional(), // Comment
                new Optional(), // PK
                new NotNull(), // WorkColumn Name
                new Optional(), // WorkColumn index
                new Optional(), //comment
                new NotNull(), //Type
                new NotNull(), //length
                new Optional(), //scale
                new Optional(), // nullable
                new Optional(), //default
                new Optional() // sample data
        };
        try {
            beanWriter = new CsvBeanWriter(new FileWriter(csvFileName, true),
                    CsvPreference.STANDARD_PREFERENCE);
            for (WorkTableDetails aTable : listTables) {
                beanWriter.write(aTable, nameMapping, processors);
            }
        } catch (IOException ex) {
            System.err.println("Error writing the CSV file: " + ex);
        } finally {
            if (beanWriter != null) {
                try {
                    beanWriter.close();
                } catch (IOException ex) {
                    System.err.println("Error closing the writer: " + ex);
                }
            }
        }
    }
}
