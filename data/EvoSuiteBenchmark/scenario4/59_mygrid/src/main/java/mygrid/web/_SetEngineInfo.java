package mygrid.web;

public class _SetEngineInfo implements java.io.Serializable {

    private java.lang.String name;

    private float cpu;

    private float ram;

    private java.lang.String os;

    public _SetEngineInfo() {
    }

    public java.lang.String getName();

    public void setName(java.lang.String name);

    public float getCpu();

    public void setCpu(float cpu);

    public float getRam();

    public void setRam(float ram);

    public java.lang.String getOs();

    public void setOs(java.lang.String os);

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj);

    private boolean __hashCodeCalc = false;

    public synchronized int hashCode();

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(_SetEngineInfo.class);

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
