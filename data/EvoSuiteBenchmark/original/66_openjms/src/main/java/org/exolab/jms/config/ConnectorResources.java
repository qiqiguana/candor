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
 * The connectorResources element is the root element of all
 * connector 
 *  resource documents.
 *  
 * 
 * @version $Revision$ $Date$
 */
public class ConnectorResources implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _connectorResourceList
     */
    private java.util.ArrayList _connectorResourceList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ConnectorResources() {
        super();
        _connectorResourceList = new ArrayList();
    } //-- org.exolab.jms.config.ConnectorResources()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addConnectorResource
     * 
     * @param vConnectorResource
     */
    public void addConnectorResource(org.exolab.jms.config.ConnectorResource vConnectorResource)
        throws java.lang.IndexOutOfBoundsException
    {
        _connectorResourceList.add(vConnectorResource);
    } //-- void addConnectorResource(org.exolab.jms.config.ConnectorResource) 

    /**
     * Method addConnectorResource
     * 
     * @param index
     * @param vConnectorResource
     */
    public void addConnectorResource(int index, org.exolab.jms.config.ConnectorResource vConnectorResource)
        throws java.lang.IndexOutOfBoundsException
    {
        _connectorResourceList.add(index, vConnectorResource);
    } //-- void addConnectorResource(int, org.exolab.jms.config.ConnectorResource) 

    /**
     * Method clearConnectorResource
     */
    public void clearConnectorResource()
    {
        _connectorResourceList.clear();
    } //-- void clearConnectorResource() 

    /**
     * Method enumerateConnectorResource
     */
    public java.util.Enumeration enumerateConnectorResource()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_connectorResourceList.iterator());
    } //-- java.util.Enumeration enumerateConnectorResource() 

    /**
     * Method getConnectorResource
     * 
     * @param index
     */
    public org.exolab.jms.config.ConnectorResource getConnectorResource(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _connectorResourceList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (org.exolab.jms.config.ConnectorResource) _connectorResourceList.get(index);
    } //-- org.exolab.jms.config.ConnectorResource getConnectorResource(int) 

    /**
     * Method getConnectorResource
     */
    public org.exolab.jms.config.ConnectorResource[] getConnectorResource()
    {
        int size = _connectorResourceList.size();
        org.exolab.jms.config.ConnectorResource[] mArray = new org.exolab.jms.config.ConnectorResource[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (org.exolab.jms.config.ConnectorResource) _connectorResourceList.get(index);
        }
        return mArray;
    } //-- org.exolab.jms.config.ConnectorResource[] getConnectorResource() 

    /**
     * Method getConnectorResourceCount
     */
    public int getConnectorResourceCount()
    {
        return _connectorResourceList.size();
    } //-- int getConnectorResourceCount() 

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
     * Method removeConnectorResource
     * 
     * @param vConnectorResource
     */
    public boolean removeConnectorResource(org.exolab.jms.config.ConnectorResource vConnectorResource)
    {
        boolean removed = _connectorResourceList.remove(vConnectorResource);
        return removed;
    } //-- boolean removeConnectorResource(org.exolab.jms.config.ConnectorResource) 

    /**
     * Method setConnectorResource
     * 
     * @param index
     * @param vConnectorResource
     */
    public void setConnectorResource(int index, org.exolab.jms.config.ConnectorResource vConnectorResource)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _connectorResourceList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _connectorResourceList.set(index, vConnectorResource);
    } //-- void setConnectorResource(int, org.exolab.jms.config.ConnectorResource) 

    /**
     * Method setConnectorResource
     * 
     * @param connectorResourceArray
     */
    public void setConnectorResource(org.exolab.jms.config.ConnectorResource[] connectorResourceArray)
    {
        //-- copy array
        _connectorResourceList.clear();
        for (int i = 0; i < connectorResourceArray.length; i++) {
            _connectorResourceList.add(connectorResourceArray[i]);
        }
    } //-- void setConnectorResource(org.exolab.jms.config.ConnectorResource) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static org.exolab.jms.config.ConnectorResources unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (org.exolab.jms.config.ConnectorResources) Unmarshaller.unmarshal(org.exolab.jms.config.ConnectorResources.class, reader);
    } //-- org.exolab.jms.config.ConnectorResources unmarshal(java.io.Reader) 

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
