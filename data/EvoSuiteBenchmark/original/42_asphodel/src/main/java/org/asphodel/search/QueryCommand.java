package org.asphodel.search;

/**
 * @author sunwj
 * @version 0.1
 * @since 0.1
 *        Date: Apr 7, 2007
 *        Time: 10:43:58 PM
 */
public class QueryCommand {
    private String queryString;
    /**
     * last query string
     */
    private String lastQuery;

    //start from 0,
    private int startIndex;

    private int maxSize;

    private char queryMode = QUERY_MODE_NEW;

    /**
     * just begin a new query
     */
    public static final char QUERY_MODE_NEW = 'N';

    /**
     * query among the current result
     */
    public static final char QUERY_MODE_IN = 'A';


    public QueryCommand(String queryString) {
        this.queryString = queryString;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getLastQuery() {
        return lastQuery;
    }

    public void setLastQuery(String lastQuery) {
        this.lastQuery = lastQuery;
    }


    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }


    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public char getQueryMode() {
        return queryMode;
    }

    public void setQueryMode(char queryMode) {
        this.queryMode = queryMode;
    }
}
