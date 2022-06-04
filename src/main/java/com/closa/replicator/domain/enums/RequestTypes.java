package com.closa.replicator.domain.enums;

public enum RequestTypes {
    //usageInfoTable, //   -> only on one table, analisys on usage of columns. Note that the table should reflect actual and realistic usage
    usageInfoTableList, //  -> on a list of tables, analisys on usage of columns. Note that the table should reflect actual and realistic usage
    //cloneEmptyTable, //  -> only on one table, clone the structure of the table, with or without indexes
    cloneEmptyTableList, // -> on a list of tables, clone the structure of the table, with or without indexes
    //cloneTableWithData, // -> only on one table, clone the structure of the table, with or without indexes AND copy the data contents
    cloneTableListWithData, // -> on a list of tables, clone the structure of the table, with or without indexes AND copy the data contents
    //cloneTableWithDataWithoutWaste, // -> only on une table, clone table, copy data and remoce any not used columns
    cloneTableListWithDataWithoutWaste, //  -> on a list of tables, clone table, copy data and remoce any not used columns
    copyTableListOnlyData, // -> on a list of tables, copy the data (expects that table is already there with same structure)
    //reportTableStruct, //  -> report on a csv file the data structure of a table
    reportTableListStruct // -> report on a csv file the data structure of a table
}
