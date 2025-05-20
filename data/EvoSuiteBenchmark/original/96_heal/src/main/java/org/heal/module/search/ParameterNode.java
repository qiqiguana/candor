package org.heal.module.search;

import java.io.Serializable;

/**
 * The ParameterNode class stores a single value from user input in the form of
 * columnInfo, keyword, and relToNext. Where columnInfo stores TableName.Column,
 * keyword stores the value of user input, relToNext stores either one of "AND,
 * OR, NOT" relations to the next ParameterNode
 */

public class ParameterNode implements Serializable {
    private String columnInfo;
    private String keyword;
    private String relToNext;
    private boolean exact;

    public ParameterNode() {
        columnInfo = null;
        keyword = null;
        relToNext = null;
        exact = false;
    }

    public ParameterNode(String column, String word, String relation) {
        columnInfo = column;
        keyword = word;
        relToNext = relation;
        exact = false;
    }

    public ParameterNode(String column, String word, String relation, boolean type) {
        columnInfo = column;
        keyword = word;
        relToNext = relation;
        exact = type;
    }

    public ParameterNode(String column, String word) {
        columnInfo = column;
        keyword = word;
        relToNext = "";
        exact = false;
    }

    /**
     * Accessor method, returns the column name as String
     * @return
     */
    public String getColumnName() {
        int pos = columnInfo.indexOf('.');
        String columnName = columnInfo.substring(pos + 1);
        return columnName;
    }

    /**
     * Accessor method, returns the table name as String
     * @return
     */
    public String getTableName() {
        int pos = columnInfo.indexOf('.');
        String tableName = columnInfo.substring(0, pos);
        return tableName;
    }

    /**
     * Accessor method, returns the tablename and columnname as String
     * @return
     */
    public String getColumnInfo() {
        return columnInfo;
    }

    /**
     * Accessor method, returns the key word as String
     * @return
     */
    public String getKeyWord() {
        return keyword;
    }

    /**
     * Accessor method, returns the relation to the next ParameterNode
     * @return
     */
    public String getRelation() {
        return relToNext;
    }

    /**
     * Accessor method, returns a boolean vairable to indicate exact search
     * @return
     */
    public boolean getExact() {
        return exact;
    }

    /**
     * Mutator, set the columnInfo (tablename.columnname) as specified
     * @param Info
     */
    public void setColumnInfo(String Info) {
        columnInfo = Info;
    }

    /**
     * Mutator, set the keyword as specified
     * @param word
     */
    public void setKeyWord(String word) {
        keyword = word;
    }

    /**
     * Mutator, set the relation as specified.
     * @param relation
     */
    public void setRelation(String relation) {
        relToNext = relation;
    }

    /**
     * Mutator, set the exact flag
     * @param type
     */
    public void setExact(boolean type) {
        exact = type;
    }
}
