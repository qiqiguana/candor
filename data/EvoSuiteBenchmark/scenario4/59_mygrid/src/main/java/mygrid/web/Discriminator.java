package mygrid.web;

public class Discriminator implements java.io.Serializable {

    private mygrid.web.ArrayOfString allowedEngines;

    private mygrid.web.Processor processor;

    private float CPU;

    private float RAM;

    private java.lang.String OS;

    public Discriminator() {
    }

    public mygrid.web.ArrayOfString getAllowedEngines();

    public void setAllowedEngines(mygrid.web.ArrayOfString allowedEngines);

    public mygrid.web.Processor getProcessor();

    public void setProcessor(mygrid.web.Processor processor);

    public float getCPU();

    public void setCPU(float CPU);

    public float getRAM();

    public void setRAM(float RAM);

    public java.lang.String getOS();

    public void setOS(java.lang.String OS);

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj);

    private boolean __hashCodeCalc = false;

    public synchronized int hashCode();

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(Discriminator.class);

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
