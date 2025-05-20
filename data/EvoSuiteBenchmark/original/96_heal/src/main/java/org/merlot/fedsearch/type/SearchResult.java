/**
 * SearchResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package org.merlot.fedsearch.type;

public class SearchResult  implements java.io.Serializable {
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

    public SearchResult(
           java.lang.String category,
           java.lang.String comments,
           int endIndex,
           java.lang.String query,
           org.merlot.fedsearch.type.SearchResultElement[] resultElements,
           float searchTime,
           int startIndex,
           int totalResultsCount) {
           this.category = category;
           this.comments = comments;
           this.endIndex = endIndex;
           this.query = query;
           this.resultElements = resultElements;
           this.searchTime = searchTime;
           this.startIndex = startIndex;
           this.totalResultsCount = totalResultsCount;
    }


    /**
     * Gets the category value for this SearchResult.
     * 
     * @return category
     */
    public java.lang.String getCategory() {
        return category;
    }


    /**
     * Sets the category value for this SearchResult.
     * 
     * @param category
     */
    public void setCategory(java.lang.String category) {
        this.category = category;
    }


    /**
     * Gets the comments value for this SearchResult.
     * 
     * @return comments
     */
    public java.lang.String getComments() {
        return comments;
    }


    /**
     * Sets the comments value for this SearchResult.
     * 
     * @param comments
     */
    public void setComments(java.lang.String comments) {
        this.comments = comments;
    }


    /**
     * Gets the endIndex value for this SearchResult.
     * 
     * @return endIndex
     */
    public int getEndIndex() {
        return endIndex;
    }


    /**
     * Sets the endIndex value for this SearchResult.
     * 
     * @param endIndex
     */
    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }


    /**
     * Gets the query value for this SearchResult.
     * 
     * @return query
     */
    public java.lang.String getQuery() {
        return query;
    }


    /**
     * Sets the query value for this SearchResult.
     * 
     * @param query
     */
    public void setQuery(java.lang.String query) {
        this.query = query;
    }


    /**
     * Gets the resultElements value for this SearchResult.
     * 
     * @return resultElements
     */
    public org.merlot.fedsearch.type.SearchResultElement[] getResultElements() {
        return resultElements;
    }


    /**
     * Sets the resultElements value for this SearchResult.
     * 
     * @param resultElements
     */
    public void setResultElements(org.merlot.fedsearch.type.SearchResultElement[] resultElements) {
        this.resultElements = resultElements;
    }


    /**
     * Gets the searchTime value for this SearchResult.
     * 
     * @return searchTime
     */
    public float getSearchTime() {
        return searchTime;
    }


    /**
     * Sets the searchTime value for this SearchResult.
     * 
     * @param searchTime
     */
    public void setSearchTime(float searchTime) {
        this.searchTime = searchTime;
    }


    /**
     * Gets the startIndex value for this SearchResult.
     * 
     * @return startIndex
     */
    public int getStartIndex() {
        return startIndex;
    }


    /**
     * Sets the startIndex value for this SearchResult.
     * 
     * @param startIndex
     */
    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }


    /**
     * Gets the totalResultsCount value for this SearchResult.
     * 
     * @return totalResultsCount
     */
    public int getTotalResultsCount() {
        return totalResultsCount;
    }


    /**
     * Sets the totalResultsCount value for this SearchResult.
     * 
     * @param totalResultsCount
     */
    public void setTotalResultsCount(int totalResultsCount) {
        this.totalResultsCount = totalResultsCount;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SearchResult)) return false;
        SearchResult other = (SearchResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.category==null && other.getCategory()==null) || 
             (this.category!=null &&
              this.category.equals(other.getCategory()))) &&
            ((this.comments==null && other.getComments()==null) || 
             (this.comments!=null &&
              this.comments.equals(other.getComments()))) &&
            this.endIndex == other.getEndIndex() &&
            ((this.query==null && other.getQuery()==null) || 
             (this.query!=null &&
              this.query.equals(other.getQuery()))) &&
            ((this.resultElements==null && other.getResultElements()==null) || 
             (this.resultElements!=null &&
              java.util.Arrays.equals(this.resultElements, other.getResultElements()))) &&
            this.searchTime == other.getSearchTime() &&
            this.startIndex == other.getStartIndex() &&
            this.totalResultsCount == other.getTotalResultsCount();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getCategory() != null) {
            _hashCode += getCategory().hashCode();
        }
        if (getComments() != null) {
            _hashCode += getComments().hashCode();
        }
        _hashCode += getEndIndex();
        if (getQuery() != null) {
            _hashCode += getQuery().hashCode();
        }
        if (getResultElements() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getResultElements());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getResultElements(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += new Float(getSearchTime()).hashCode();
        _hashCode += getStartIndex();
        _hashCode += getTotalResultsCount();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SearchResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedsearch.merlot.org/type", "SearchResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("category");
        elemField.setXmlName(new javax.xml.namespace.QName("", "category"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comments");
        elemField.setXmlName(new javax.xml.namespace.QName("", "comments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endIndex");
        elemField.setXmlName(new javax.xml.namespace.QName("", "endIndex"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("query");
        elemField.setXmlName(new javax.xml.namespace.QName("", "query"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultElements");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resultElements"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedsearch.merlot.org/type", "SearchResultElement"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("searchTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "searchTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startIndex");
        elemField.setXmlName(new javax.xml.namespace.QName("", "startIndex"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalResultsCount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "totalResultsCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
