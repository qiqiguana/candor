package org.merlot.fedsearch.type;

public class SearchResultElement implements java.io.Serializable {

    private java.lang.String URL;

    private java.lang.String assignmentsURL;

    private java.lang.String authorName;

    private int avgComments;

    private int avgPeerReviews;

    private java.lang.String award;

    private java.lang.String commentsURL;

    private java.lang.String creationDate;

    private java.lang.String description;

    private java.lang.String detailURL;

    private java.lang.String itemType;

    private int numAssignments;

    private int numComments;

    private int numPeerReviews;

    private java.lang.String peerReviewsURL;

    private int relevanceRanking;

    private java.lang.String title;

    public SearchResultElement() {
    }

    public SearchResultElement(java.lang.String URL, java.lang.String assignmentsURL, java.lang.String authorName, int avgComments, int avgPeerReviews, java.lang.String award, java.lang.String commentsURL, java.lang.String creationDate, java.lang.String description, java.lang.String detailURL, java.lang.String itemType, int numAssignments, int numComments, int numPeerReviews, java.lang.String peerReviewsURL, int relevanceRanking, java.lang.String title) {
    }

    /**
     * Gets the URL value for this SearchResultElement.
     *
     * @return URL
     */
    public java.lang.String getURL();

    /**
     * Sets the URL value for this SearchResultElement.
     *
     * @param URL
     */
    public void setURL(java.lang.String URL);

    /**
     * Gets the assignmentsURL value for this SearchResultElement.
     *
     * @return assignmentsURL
     */
    public java.lang.String getAssignmentsURL();

    /**
     * Sets the assignmentsURL value for this SearchResultElement.
     *
     * @param assignmentsURL
     */
    public void setAssignmentsURL(java.lang.String assignmentsURL);

    /**
     * Gets the authorName value for this SearchResultElement.
     *
     * @return authorName
     */
    public java.lang.String getAuthorName();

    /**
     * Sets the authorName value for this SearchResultElement.
     *
     * @param authorName
     */
    public void setAuthorName(java.lang.String authorName);

    /**
     * Gets the avgComments value for this SearchResultElement.
     *
     * @return avgComments
     */
    public int getAvgComments();

    /**
     * Sets the avgComments value for this SearchResultElement.
     *
     * @param avgComments
     */
    public void setAvgComments(int avgComments);

    /**
     * Gets the avgPeerReviews value for this SearchResultElement.
     *
     * @return avgPeerReviews
     */
    public int getAvgPeerReviews();

    /**
     * Sets the avgPeerReviews value for this SearchResultElement.
     *
     * @param avgPeerReviews
     */
    public void setAvgPeerReviews(int avgPeerReviews);

    /**
     * Gets the award value for this SearchResultElement.
     *
     * @return award
     */
    public java.lang.String getAward();

    /**
     * Sets the award value for this SearchResultElement.
     *
     * @param award
     */
    public void setAward(java.lang.String award);

    /**
     * Gets the commentsURL value for this SearchResultElement.
     *
     * @return commentsURL
     */
    public java.lang.String getCommentsURL();

    /**
     * Sets the commentsURL value for this SearchResultElement.
     *
     * @param commentsURL
     */
    public void setCommentsURL(java.lang.String commentsURL);

    /**
     * Gets the creationDate value for this SearchResultElement.
     *
     * @return creationDate
     */
    public java.lang.String getCreationDate();

    /**
     * Sets the creationDate value for this SearchResultElement.
     *
     * @param creationDate
     */
    public void setCreationDate(java.lang.String creationDate);

    /**
     * Gets the description value for this SearchResultElement.
     *
     * @return description
     */
    public java.lang.String getDescription();

    /**
     * Sets the description value for this SearchResultElement.
     *
     * @param description
     */
    public void setDescription(java.lang.String description);

    /**
     * Gets the detailURL value for this SearchResultElement.
     *
     * @return detailURL
     */
    public java.lang.String getDetailURL();

    /**
     * Sets the detailURL value for this SearchResultElement.
     *
     * @param detailURL
     */
    public void setDetailURL(java.lang.String detailURL);

    /**
     * Gets the itemType value for this SearchResultElement.
     *
     * @return itemType
     */
    public java.lang.String getItemType();

    /**
     * Sets the itemType value for this SearchResultElement.
     *
     * @param itemType
     */
    public void setItemType(java.lang.String itemType);

    /**
     * Gets the numAssignments value for this SearchResultElement.
     *
     * @return numAssignments
     */
    public int getNumAssignments();

    /**
     * Sets the numAssignments value for this SearchResultElement.
     *
     * @param numAssignments
     */
    public void setNumAssignments(int numAssignments);

    /**
     * Gets the numComments value for this SearchResultElement.
     *
     * @return numComments
     */
    public int getNumComments();

    /**
     * Sets the numComments value for this SearchResultElement.
     *
     * @param numComments
     */
    public void setNumComments(int numComments);

    /**
     * Gets the numPeerReviews value for this SearchResultElement.
     *
     * @return numPeerReviews
     */
    public int getNumPeerReviews();

    /**
     * Sets the numPeerReviews value for this SearchResultElement.
     *
     * @param numPeerReviews
     */
    public void setNumPeerReviews(int numPeerReviews);

    /**
     * Gets the peerReviewsURL value for this SearchResultElement.
     *
     * @return peerReviewsURL
     */
    public java.lang.String getPeerReviewsURL();

    /**
     * Sets the peerReviewsURL value for this SearchResultElement.
     *
     * @param peerReviewsURL
     */
    public void setPeerReviewsURL(java.lang.String peerReviewsURL);

    /**
     * Gets the relevanceRanking value for this SearchResultElement.
     *
     * @return relevanceRanking
     */
    public int getRelevanceRanking();

    /**
     * Sets the relevanceRanking value for this SearchResultElement.
     *
     * @param relevanceRanking
     */
    public void setRelevanceRanking(int relevanceRanking);

    /**
     * Gets the title value for this SearchResultElement.
     *
     * @return title
     */
    public java.lang.String getTitle();

    /**
     * Sets the title value for this SearchResultElement.
     *
     * @param title
     */
    public void setTitle(java.lang.String title);

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj);

    private boolean __hashCodeCalc = false;

    public synchronized int hashCode();

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(SearchResultElement.class, true);

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
