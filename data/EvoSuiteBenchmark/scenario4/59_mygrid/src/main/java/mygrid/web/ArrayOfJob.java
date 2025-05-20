package mygrid.web;

public class ArrayOfJob implements java.io.Serializable {

    private mygrid.web.Job[] job;

    public ArrayOfJob() {
    }

    public mygrid.web.Job[] getJob();

    public void setJob(mygrid.web.Job[] job);

    public mygrid.web.Job getJob(int i);

    public void setJob(int i, mygrid.web.Job value);

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj);

    private boolean __hashCodeCalc = false;

    public synchronized int hashCode();

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(ArrayOfJob.class);

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
