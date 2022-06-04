package com.closa.replicator.domain.enums;

public enum RelationOperator {
    EQUAL_TO ("=", "===", "==", "EQ" ),
    NOT_EQUAL_TO("<>", "!==", "!=", "NE"),
    GREATER_THAN(">",">", ">", "GT"),
    LESS_THAN("<", "LT"),
    LIKE("like", "LK"),
    CONTAINS("contains", "CT"),
    STARTS_WITH("starts with", "SW"),
    ENDS_WITH("ends with", "EW");

    private String logicalSymbol;
    private String jsSymbol;
    private String javaSymbol;
    private String shortCode;

    RelationOperator(String logicalSymbol, String shortCode) {
        this.logicalSymbol = logicalSymbol;
        this.shortCode = shortCode;
    }

    RelationOperator(String logicalSymbol, String jsSymbol, String javaSymbol, String shortCode) {
        this.logicalSymbol = logicalSymbol;
        this.jsSymbol = jsSymbol;
        this.javaSymbol = javaSymbol;
        this.shortCode = shortCode;
    }

    public String getLogicalSymbol() {
        return logicalSymbol;
    }

    public void setLogicalSymbol(String logicalSymbol) {
        this.logicalSymbol = logicalSymbol;
    }

    public String getJsSymbol() {
        return jsSymbol;
    }

    public void setJsSymbol(String jsSymbol) {
        this.jsSymbol = jsSymbol;
    }

    public String getJavaSymbol() {
        return javaSymbol;
    }

    public void setJavaSymbol(String javaSymbol) {
        this.javaSymbol = javaSymbol;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }
}
