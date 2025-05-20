/**
            * Invoke.java
            *
            * This file was auto-generated from WSDL
            * by the Apache Axis2 version: #axisVersion# #today#
            */
package com.densebrain.rif.client.service.types;

/**
 * Invoke bean class
 */
public class Invoke implements org.apache.axis2.databinding.ADBBean {
    public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://densebrain.com/rif/client/service/types",
            "invoke", "ns1");

    /** field for ClassName */
    protected java.lang.String localClassName;

    /** field for MethodName */
    protected java.lang.String localMethodName;

    /** field for SerializedParams */
    protected java.lang.String localSerializedParams;

    /**
     * Auto generated getter method
     *
     * @return java.lang.String
     */
    public java.lang.String getClassName() {
        return localClassName;
    }

    /**
     * Auto generated setter method
     *
     * @param param ClassName
     */
    public void setClassName(java.lang.String param) {
        this.localClassName = param;
    }

    /**
     * Auto generated getter method
     *
     * @return java.lang.String
     */
    public java.lang.String getMethodName() {
        return localMethodName;
    }

    /**
     * Auto generated setter method
     *
     * @param param MethodName
     */
    public void setMethodName(java.lang.String param) {
        this.localMethodName = param;
    }

    /**
     * Auto generated getter method
     *
     * @return java.lang.String
     */
    public java.lang.String getSerializedParams() {
        return localSerializedParams;
    }

    /**
     * Auto generated setter method
     *
     * @param param SerializedParams
     */
    public void setSerializedParams(java.lang.String param) {
        this.localSerializedParams = param;
    }

    /**
     * databinding method to get an XML representation of this object
     */
    public javax.xml.stream.XMLStreamReader getPullParser(
        javax.xml.namespace.QName qName) {
        java.util.ArrayList elementList = new java.util.ArrayList();
        java.util.ArrayList attribList = new java.util.ArrayList();

        elementList.add(new javax.xml.namespace.QName("", "className"));

        elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localClassName));

        elementList.add(new javax.xml.namespace.QName("", "methodName"));

        elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localMethodName));

        elementList.add(new javax.xml.namespace.QName("", "serializedParams"));

        elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localSerializedParams));

        return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName,
            elementList.toArray(), attribList.toArray());
    }

    /**
     * Factory class that keeps the parse method
     */
    public static class Factory {
        // This is horrible, but the OM implementation of getElementText() does not obey the proper contract.  Specifically, it does
        // does not advance the reader to the END_ELEMENT.  This bug is triggered by calls to getElementText() unpredictably, e.g. it
        // happens with outer (document) elements, but not with inner elements.  The root bug is in OMStAXWrapper.java, which is now part
        // of commons and so cannot just be fixed in axis2.  This method should be removed and the calls to it below replaced with
        // simple calls to getElementText() as soon as this serious bug can be fixed.
        private static java.lang.String getElementTextProperly(
            javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
            java.lang.String value = reader.getElementText();

            while (!reader.isEndElement())
                reader.next();

            return value;
        }

        /**
         * static method to create the object Precondition:  If
         * this object is an element, the current or next start element starts
         * this object and any intervening reader events are ignorable If this
         * object is not an element, it is a complex type and the reader is at
         * the event just after the outer start element Postcondition: If this
         * object is an element, the reader is positioned at its end element
         * If this object is a complex type, the reader is positioned at the
         * end element of its outer element
         */
        public static Invoke parse(javax.xml.stream.XMLStreamReader reader)
            throws java.lang.Exception {
            Invoke object = new Invoke();
            int event;

            try {
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                reader.next();

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "className").equals(
                            reader.getName())) {
                    java.lang.String content = getElementTextProperly(reader);
                    object.setClassName(org.apache.axis2.databinding.utils.ConverterUtil.convertTostring(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                    // A start element we are not expecting indicates an invalid parameter was passed
                    throw new java.lang.RuntimeException(
                        "Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "methodName").equals(
                            reader.getName())) {
                    java.lang.String content = getElementTextProperly(reader);
                    object.setMethodName(org.apache.axis2.databinding.utils.ConverterUtil.convertTostring(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                    // A start element we are not expecting indicates an invalid parameter was passed
                    throw new java.lang.RuntimeException(
                        "Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "serializedParams").equals(
                            reader.getName())) {
                    java.lang.String content = getElementTextProperly(reader);
                    object.setSerializedParams(org.apache.axis2.databinding.utils.ConverterUtil.convertTostring(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                    // A start element we are not expecting indicates an invalid parameter was passed
                    throw new java.lang.RuntimeException(
                        "Unexpected subelement " + reader.getLocalName());
                }
            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }
    } //end of factory class
}
