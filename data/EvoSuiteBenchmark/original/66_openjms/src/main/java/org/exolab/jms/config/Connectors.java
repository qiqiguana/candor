/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.4.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.exolab.jms.config;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Enumeration;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class Connectors.
 * 
 * @version $Revision$ $Date$
 */
public class Connectors implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * This specifies the type of communication to use, between
     * clients
     *  and the server.
     *  
     */
    private java.util.ArrayList _connectorList;


      //----------------/
     //- Constructors -/
    //----------------/

    public Connectors() {
        super();
        _connectorList = new ArrayList();
    } //-- org.exolab.jms.config.Connectors()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addConnector
     * 
     * @param vConnector
     */
    public void addConnector(org.exolab.jms.config.Connector vConnector)
        throws java.lang.IndexOutOfBoundsException
    {
        _connectorList.add(vConnector);
    } //-- void addConnector(org.exolab.jms.config.Connector) 

    /**
     * Method addConnector
     * 
     * @param index
     * @param vConnector
     */
    public void addConnector(int index, org.exolab.jms.config.Connector vConnector)
        throws java.lang.IndexOutOfBoundsException
    {
        _connectorList.add(index, vConnector);
    } //-- void addConnector(int, org.exolab.jms.config.Connector) 

    /**
     * Method clearConnector
     */
    public void clearConnector()
    {
        _connectorList.clear();
    } //-- void clearConnector() 

    /**
     * Method enumerateConnector
     */
    public java.util.Enumeration enumerateConnector()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_connectorList.iterator());
    } //-- java.util.Enumeration enumerateConnector() 

    /**
     * Method getConnector
     * 
     * @param index
     */
    public org.exolab.jms.config.Connector getConnector(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _connectorList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (org.exolab.jms.config.Connector) _connectorList.get(index);
    } //-- org.exolab.jms.config.Connector getConnector(int) 

    /**
     * Method getConnector
     */
    public org.exolab.jms.config.Connector[] getConnector()
    {
        int size = _connectorList.size();
        org.exolab.jms.config.Connector[] mArray = new org.exolab.jms.config.Connector[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (org.exolab.jms.config.Connector) _connectorList.get(index);
        }
        return mArray;
    } //-- org.exolab.jms.config.Connector[] getConnector() 

    /**
     * Method getConnectorCount
     */
    public int getConnectorCount()
    {
        return _connectorList.size();
    } //-- int getConnectorCount() 

    /**
     * Method isValid
     */
    public boolean isValid()
    {
        try {
            validate();
        }
        catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    } //-- boolean isValid() 

    /**
     * Method marshal
     * 
     * @param out
     */
    public void marshal(java.io.Writer out)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, out);
    } //-- void marshal(java.io.Writer) 

    /**
     * Method marshal
     * 
     * @param handler
     */
    public void marshal(org.xml.sax.ContentHandler handler)
        throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, handler);
    } //-- void marshal(org.xml.sax.ContentHandler) 

    /**
     * Method removeConnector
     * 
     * @param vConnector
     */
    public boolean removeConnector(org.exolab.jms.config.Connector vConnector)
    {
        boolean removed = _connectorList.remove(vConnector);
        return removed;
    } //-- boolean removeConnector(org.exolab.jms.config.Connector) 

    /**
     * Method setConnector
     * 
     * @param index
     * @param vConnector
     */
    public void setConnector(int index, org.exolab.jms.config.Connector vConnector)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _connectorList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _connectorList.set(index, vConnector);
    } //-- void setConnector(int, org.exolab.jms.config.Connector) 

    /**
     * Method setConnector
     * 
     * @param connectorArray
     */
    public void setConnector(org.exolab.jms.config.Connector[] connectorArray)
    {
        //-- copy array
        _connectorList.clear();
        for (int i = 0; i < connectorArray.length; i++) {
            _connectorList.add(connectorArray[i]);
        }
    } //-- void setConnector(org.exolab.jms.config.Connector) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static org.exolab.jms.config.Connectors unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (org.exolab.jms.config.Connectors) Unmarshaller.unmarshal(org.exolab.jms.config.Connectors.class, reader);
    } //-- org.exolab.jms.config.Connectors unmarshal(java.io.Reader) 

    /**
     * Method validate
     */
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}
