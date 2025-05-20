package mygrid.web;

public class Engine extends mygrid.web.MarshalByRefObject implements java.io.Serializable {

    private java.lang.String id;

    private java.lang.String name;

    private float RAM;

    private float CPU;

    private java.lang.String OS;

    public Engine() {
    }

    public java.lang.String getId();

    public void setId(java.lang.String id);

    public java.lang.String getName();

    public void setName(java.lang.String name);

    public float getRAM();

    public void setRAM(float RAM);

    public float getCPU();

    public void setCPU(float CPU);

    public java.lang.String getOS();

    public void setOS(java.lang.String OS);

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj);

    private boolean __hashCodeCalc = false;

    public synchronized int hashCode();

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(Engine.class);

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
