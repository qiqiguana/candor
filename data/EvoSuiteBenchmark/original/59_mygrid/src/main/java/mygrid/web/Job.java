/**
 * Job.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package mygrid.web;

public class Job  implements java.io.Serializable {
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

    public java.lang.String getId() {
        return id;
    }

    public void setId(java.lang.String id) {
        this.id = id;
    }

    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public mygrid.web.Discriminator getDiscriminators() {
        return discriminators;
    }

    public void setDiscriminators(mygrid.web.Discriminator discriminators) {
        this.discriminators = discriminators;
    }

    public mygrid.web.ArrayOfDependency getDependencies() {
        return dependencies;
    }

    public void setDependencies(mygrid.web.ArrayOfDependency dependencies) {
        this.dependencies = dependencies;
    }

    public mygrid.web.ArrayOfContextElement getContext() {
        return context;
    }

    public void setContext(mygrid.web.ArrayOfContextElement context) {
        this.context = context;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public boolean isBroadcasted() {
        return broadcasted;
    }

    public void setBroadcasted(boolean broadcasted) {
        this.broadcasted = broadcasted;
    }

    public java.lang.String getCurrentEngineId() {
        return currentEngineId;
    }

    public void setCurrentEngineId(java.lang.String currentEngineId) {
        this.currentEngineId = currentEngineId;
    }

    public mygrid.web.GridJobStatus getStatus() {
        return status;
    }

    public void setStatus(mygrid.web.GridJobStatus status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Job)) return false;
        Job other = (Job) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.discriminators==null && other.getDiscriminators()==null) || 
             (this.discriminators!=null &&
              this.discriminators.equals(other.getDiscriminators()))) &&
            ((this.dependencies==null && other.getDependencies()==null) || 
             (this.dependencies!=null &&
              this.dependencies.equals(other.getDependencies()))) &&
            ((this.context==null && other.getContext()==null) || 
             (this.context!=null &&
              this.context.equals(other.getContext()))) &&
            this.progress == other.getProgress() &&
            this.broadcasted == other.isBroadcasted() &&
            ((this.currentEngineId==null && other.getCurrentEngineId()==null) || 
             (this.currentEngineId!=null &&
              this.currentEngineId.equals(other.getCurrentEngineId()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus())));
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
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getDiscriminators() != null) {
            _hashCode += getDiscriminators().hashCode();
        }
        if (getDependencies() != null) {
            _hashCode += getDependencies().hashCode();
        }
        if (getContext() != null) {
            _hashCode += getContext().hashCode();
        }
        _hashCode += getProgress();
        _hashCode += new Boolean(isBroadcasted()).hashCode();
        if (getCurrentEngineId() != null) {
            _hashCode += getCurrentEngineId().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Job.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "Job"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("discriminators");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Discriminators"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "Discriminator"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dependencies");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Dependencies"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "ArrayOfDependency"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("context");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Context"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "ArrayOfContextElement"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("progress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Progress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("broadcasted");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Broadcasted"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currentEngineId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "CurrentEngineId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "GridJobStatus"));
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
