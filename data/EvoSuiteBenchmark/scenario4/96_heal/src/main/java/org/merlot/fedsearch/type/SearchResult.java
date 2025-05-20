package org.merlot.fedsearch.type;

public class SearchResult implements java.io.Serializable {

    private java.lang.String category;

    private java.lang.String comments;

    private int endIndex;

    private java.lang.String query;

    private org.merlot.fedsearch.type.SearchResultElement[] resultElements;

    private float searchTime;

    private int startIndex;

    private int totalResultsCount;

    public SearchResult() {
    }

    public SearchResult(java.lang.String category, java.lang.String comments, int endIndex, java.lang.String query, org.merlot.fedsearch.type.SearchResultElement[] resultElements, float searchTime, int startIndex, int totalResultsCount) {
    }

    /**
     * Gets the category value for this SearchResult.
     *
     * @return category
     */
    public java.lang.String getCategory();

    /**
     * Sets the category value for this SearchResult.
     *
     * @param category
     */
    public void setCategory(java.lang.String category);

    /**
     * Gets the comments value for this SearchResult.
     *
     * @return comments
     */
    public java.lang.String getComments();

    /**
     * Sets the comments value for this SearchResult.
     *
     * @param comments
     */
    public void setComments(java.lang.String comments);

    /**
     * Gets the endIndex value for this SearchResult.
     *
     * @return endIndex
     */
    public int getEndIndex();

    /**
     * Sets the endIndex value for this SearchResult.
     *
     * @param endIndex
     */
    public void setEndIndex(int endIndex);

    /**
     * Gets the query value for this SearchResult.
     *
     * @return query
     */
    public java.lang.String getQuery();

    /**
     * Sets the query value for this SearchResult.
     *
     * @param query
     */
    public void setQuery(java.lang.String query);

    /**
     * Gets the resultElements value for this SearchResult.
     *
     * @return resultElements
     */
    public org.merlot.fedsearch.type.SearchResultElement[] getResultElements();

    /**
     * Sets the resultElements value for this SearchResult.
     *
     * @param resultElements
     */
    public void setResultElements(org.merlot.fedsearch.type.SearchResultElement[] resultElements);

    /**
     * Gets the searchTime value for this SearchResult.
     *
     * @return searchTime
     */
    public float getSearchTime();

    /**
     * Sets the searchTime value for this SearchResult.
     *
     * @param searchTime
     */
    public void setSearchTime(float searchTime);

    /**
     * Gets the startIndex value for this SearchResult.
     *
     * @return startIndex
     */
    public int getStartIndex();

    /**
     * Sets the startIndex value for this SearchResult.
     *
     * @param startIndex
     */
    public void setStartIndex(int startIndex);

    /**
     * Gets the totalResultsCount value for this SearchResult.
     *
     * @return totalResultsCount
     */
    public int getTotalResultsCount();

    /**
     * Sets the totalResultsCount value for this SearchResult.
     *
     * @param totalResultsCount
     */
    public void setTotalResultsCount(int totalResultsCount);

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj);

    private boolean __hashCodeCalc = false;

    public synchronized int hashCode();

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(SearchResult.class, true);

    static {
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc();

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(java.lang.String mechType, java.lang.Class _javaType, javax.xml.namespace.QName _xmlType);

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(java.lang.String mechType, java.lang.Class _javaType, javax.xml.namespace.QName _xmlType);
}
