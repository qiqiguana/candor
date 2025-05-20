package mygrid.web;

public class _Logon implements java.io.Serializable {

    private java.lang.String clusterUrl;

    private java.lang.String user;

    private java.lang.String password;

    public _Logon() {
    }

    public java.lang.String getClusterUrl();

    public void setClusterUrl(java.lang.String clusterUrl);

    public java.lang.String getUser();

    public void setUser(java.lang.String user);

    public java.lang.String getPassword();

    public void setPassword(java.lang.String password);

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj);

    private boolean __hashCodeCalc = false;

    public synchronized int hashCode();

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(_Logon.class);

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
