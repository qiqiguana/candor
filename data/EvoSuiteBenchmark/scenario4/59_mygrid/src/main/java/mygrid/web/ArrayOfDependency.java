package mygrid.web;

public class ArrayOfDependency implements java.io.Serializable {

    private mygrid.web.Dependency[] dependency;

    public ArrayOfDependency() {
    }

    public mygrid.web.Dependency[] getDependency();

    public void setDependency(mygrid.web.Dependency[] dependency);

    public mygrid.web.Dependency getDependency(int i);

    public void setDependency(int i, mygrid.web.Dependency value);

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj);

    private boolean __hashCodeCalc = false;

    public synchronized int hashCode();

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(ArrayOfDependency.class);

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
