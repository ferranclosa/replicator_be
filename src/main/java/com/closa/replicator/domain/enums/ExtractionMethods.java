package com.closa.replicator.domain.enums;

import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * ALL means that for the file, we extract the whole of it
 * LIMITED will look for the integer in Extraction.limitTo value. When it reaches that number it will stop
 * COLUMN_VALUE will look for the Columns(0) and Values(0) to build the WHERE clause when extracting
 * COLUMNS_VALUES will look for the Columns(n) matches with the Values(n) to build the WHERE clause
 * WHERE_STATEMENT will use the Extraction.whereStatement to build the extraction SQL
 * MANY_TO_ONE will look for the file in Extraction.manyFile to build a inner join and will use the Columns(n) with Values(n) where Values(n) will contain the column names of the manyFile
 */
public enum ExtractionMethods {
    ALL("ALL means that for the file, we extract the whole of it", 0),
    LIMITED("LIMITED will look for the integer in Extraction.limitTo value. When it reaches that number it will stop", 1),
    COLUMN_VALUE("COLUMN_VALUE will look for the Columns(0) and Values(0) to build the WHERE clause when extracting", 2),
    COLUMNS_VALUES("COLUMNS_VALUES will look for the Columns(n) matches with the Values(n) to build the WHERE clause", 3),
    WHERE_STATEMENT("WHERE_STATEMENT will use the Extraction.whereStatement to build the extraction SQL", 4),
    MANY_TO_ONE("MANY_TO_ONE will look for the file in Extraction.manyFile to build a inner join and will use the Columns(n) with Values(n) where Values(n) will contain the column names of the manyFile", 5);

    private String description;
    private int order;

    ExtractionMethods(String description, int order) {
        this.description = description;
        this.order = order;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
