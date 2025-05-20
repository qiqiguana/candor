package mygrid.web;

public class Job implements java.io.Serializable {

    private java.lang.String id;

    private java.lang.String name;

    private mygrid.web.Discriminator discriminators;

    private mygrid.web.ArrayOfDependency dependencies;

    private mygrid.web.ArrayOfContextElement context;

    private int progress;

    private boolean broadcasted;

    private java.lang.String currentEngineId;

    private mygrid.web.GridJobStatus status;

    public Job() {
    }

    public java.lang.String getId();

    public void setId(java.lang.String id);

    public java.lang.String getName();

    public void setName(java.lang.String name);

    public mygrid.web.Discriminator getDiscriminators();

    public void setDiscriminators(mygrid.web.Discriminator discriminators);

    public mygrid.web.ArrayOfDependency getDependencies();

    public void setDependencies(mygrid.web.ArrayOfDependency dependencies);

    public mygrid.web.ArrayOfContextElement getContext();

    public void setContext(mygrid.web.ArrayOfContextElement context);

    public int getProgress();

    public void setProgress(int progress);

    public boolean isBroadcasted();

    public void setBroadcasted(boolean broadcasted);

    public java.lang.String getCurrentEngineId();

    public void setCurrentEngineId(java.lang.String currentEngineId);

    public mygrid.web.GridJobStatus getStatus();

    public void setStatus(mygrid.web.GridJobStatus status);

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj);

    private boolean __hashCodeCalc = false;

    public synchronized int hashCode();

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(Job.class);

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
