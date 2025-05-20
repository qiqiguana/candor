package mygrid.web;

public class ArrayOfContextElement implements java.io.Serializable {

    private mygrid.web.ContextElement[] contextElement;

    public ArrayOfContextElement() {
    }

    public mygrid.web.ContextElement[] getContextElement();

    public void setContextElement(mygrid.web.ContextElement[] contextElement);

    public mygrid.web.ContextElement getContextElement(int i);

    public void setContextElement(int i, mygrid.web.ContextElement value);

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj);

    private boolean __hashCodeCalc = false;

    public synchronized int hashCode();

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(ArrayOfContextElement.class);

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
