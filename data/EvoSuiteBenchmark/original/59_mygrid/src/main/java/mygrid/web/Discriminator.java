/**
 * Discriminator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package mygrid.web;

public class Discriminator  implements java.io.Serializable {
    private mygrid.web.ArrayOfString allowedEngines;
    private mygrid.web.Processor processor;
    private float CPU;
    private float RAM;
    private java.lang.String OS;

    public Discriminator() {
    }

    public mygrid.web.ArrayOfString getAllowedEngines() {
        return allowedEngines;
    }

    public void setAllowedEngines(mygrid.web.ArrayOfString allowedEngines) {
        this.allowedEngines = allowedEngines;
    }

    public mygrid.web.Processor getProcessor() {
        return processor;
    }

    public void setProcessor(mygrid.web.Processor processor) {
        this.processor = processor;
    }

    public float getCPU() {
        return CPU;
    }

    public void setCPU(float CPU) {
        this.CPU = CPU;
    }

    public float getRAM() {
        return RAM;
    }

    public void setRAM(float RAM) {
        this.RAM = RAM;
    }

    public java.lang.String getOS() {
        return OS;
    }

    public void setOS(java.lang.String OS) {
        this.OS = OS;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Discriminator)) return false;
        Discriminator other = (Discriminator) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.allowedEngines==null && other.getAllowedEngines()==null) || 
             (this.allowedEngines!=null &&
              this.allowedEngines.equals(other.getAllowedEngines()))) &&
            ((this.processor==null && other.getProcessor()==null) || 
             (this.processor!=null &&
              this.processor.equals(other.getProcessor()))) &&
            this.CPU == other.getCPU() &&
            this.RAM == other.getRAM() &&
            ((this.OS==null && other.getOS()==null) || 
             (this.OS!=null &&
              this.OS.equals(other.getOS())));
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
        if (getAllowedEngines() != null) {
            _hashCode += getAllowedEngines().hashCode();
        }
        if (getProcessor() != null) {
            _hashCode += getProcessor().hashCode();
        }
        _hashCode += new Float(getCPU()).hashCode();
        _hashCode += new Float(getRAM()).hashCode();
        if (getOS() != null) {
            _hashCode += getOS().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Discriminator.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "Discriminator"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("allowedEngines");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "AllowedEngines"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "ArrayOfString"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("processor");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Processor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "Processor"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CPU");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "CPU"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("RAM");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "RAM"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("OS");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "OS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
