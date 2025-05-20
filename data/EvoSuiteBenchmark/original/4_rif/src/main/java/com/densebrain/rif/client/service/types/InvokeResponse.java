/**
            * InvokeResponse.java
            *
            * This file was auto-generated from WSDL
            * by the Apache Axis2 version: #axisVersion# #today#
            */
package com.densebrain.rif.client.service.types;

/**
 * InvokeResponse bean class
 */
public class InvokeResponse implements org.apache.axis2.databinding.ADBBean {
    public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://densebrain.com/rif/client/service/types",
            "invokeResponse", "ns1");

    /** field for _return */
    protected java.lang.String local_return;

    /**
     * Auto generated getter method
     *
     * @return java.lang.String
     */
    public java.lang.String get_return() {
        return local_return;
    }

    /**
     * Auto generated setter method
     *
     * @param param _return
     */
    public void set_return(java.lang.String param) {
        this.local_return = param;
    }

    /**
     * databinding method to get an XML representation of this object
     */
    public javax.xml.stream.XMLStreamReader getPullParser(
        javax.xml.namespace.QName qName) {
        java.util.ArrayList elementList = new java.util.ArrayList();
        java.util.ArrayList attribList = new java.util.ArrayList();

        elementList.add(new javax.xml.namespace.QName("", "return"));

        elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                local_return));

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
        public static InvokeResponse parse(
            javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
            InvokeResponse object = new InvokeResponse();
            int event;

            try {
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                reader.next();

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "return").equals(
                            reader.getName())) {
                    java.lang.String content = getElementTextProperly(reader);
                    object.set_return(org.apache.axis2.databinding.utils.ConverterUtil.convertTostring(
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
