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
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * This element defines items specific to the RMI connector.
 *  
 * 
 * @version $Revision$ $Date$
 */
public class RmiConfiguration implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * This specifies whether to use an embedded (or internal) RMI
     *  registry, or an external one.
     *  
     */
    private boolean _embeddedRegistry = true;

    /**
     * keeps track of state for field: _embeddedRegistry
     */
    private boolean _has_embeddedRegistry;

    /**
     * The RMI registry host. This is only applicable if an
     * external
     *  RMI registry is being used, i.e., embeddedRegistry is
     * false.
     *  
     */
    private java.lang.String _registryHost = "localhost";

    /**
     * The RMI registry port.
     *  
     */
    private int _registryPort = 1099;

    /**
     * keeps track of state for field: _registryPort
     */
    private boolean _has_registryPort;

    /**
     * The client ping interval, specified in seconds. If set to 0,
     * the 
     *  ping is disabled.
     *  
     */
    private int _clientPingInterval = 15;

    /**
     * keeps track of state for field: _clientPingInterval
     */
    private boolean _has_clientPingInterval;


      //----------------/
     //- Constructors -/
    //----------------/

    public RmiConfiguration() {
        super();
        setRegistryHost("localhost");
    } //-- org.exolab.jms.config.RmiConfiguration()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method deleteClientPingInterval
     */
    public void deleteClientPingInterval()
    {
        this._has_clientPingInterval= false;
    } //-- void deleteClientPingInterval() 

    /**
     * Method deleteEmbeddedRegistry
     */
    public void deleteEmbeddedRegistry()
    {
        this._has_embeddedRegistry= false;
    } //-- void deleteEmbeddedRegistry() 

    /**
     * Method deleteRegistryPort
     */
    public void deleteRegistryPort()
    {
        this._has_registryPort= false;
    } //-- void deleteRegistryPort() 

    /**
     * Returns the value of field 'clientPingInterval'. The field
     * 'clientPingInterval' has the following description: The
     * client ping interval, specified in seconds. If set to 0, the
     * 
     *  ping is disabled.
     *  
     * 
     * @return the value of field 'clientPingInterval'.
     */
    public int getClientPingInterval()
    {
        return this._clientPingInterval;
    } //-- int getClientPingInterval() 

    /**
     * Returns the value of field 'embeddedRegistry'. The field
     * 'embeddedRegistry' has the following description: This
     * specifies whether to use an embedded (or internal) RMI
     *  registry, or an external one.
     *  
     * 
     * @return the value of field 'embeddedRegistry'.
     */
    public boolean getEmbeddedRegistry()
    {
        return this._embeddedRegistry;
    } //-- boolean getEmbeddedRegistry() 

    /**
     * Returns the value of field 'registryHost'. The field
     * 'registryHost' has the following description: The RMI
     * registry host. This is only applicable if an external
     *  RMI registry is being used, i.e., embeddedRegistry is
     * false.
     *  
     * 
     * @return the value of field 'registryHost'.
     */
    public java.lang.String getRegistryHost()
    {
        return this._registryHost;
    } //-- java.lang.String getRegistryHost() 

    /**
     * Returns the value of field 'registryPort'. The field
     * 'registryPort' has the following description: The RMI
     * registry port.
     *  
     * 
     * @return the value of field 'registryPort'.
     */
    public int getRegistryPort()
    {
        return this._registryPort;
    } //-- int getRegistryPort() 

    /**
     * Method hasClientPingInterval
     */
    public boolean hasClientPingInterval()
    {
        return this._has_clientPingInterval;
    } //-- boolean hasClientPingInterval() 

    /**
     * Method hasEmbeddedRegistry
     */
    public boolean hasEmbeddedRegistry()
    {
        return this._has_embeddedRegistry;
    } //-- boolean hasEmbeddedRegistry() 

    /**
     * Method hasRegistryPort
     */
    public boolean hasRegistryPort()
    {
        return this._has_registryPort;
    } //-- boolean hasRegistryPort() 

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
     * Sets the value of field 'clientPingInterval'. The field
     * 'clientPingInterval' has the following description: The
     * client ping interval, specified in seconds. If set to 0, the
     * 
     *  ping is disabled.
     *  
     * 
     * @param clientPingInterval the value of field
     * 'clientPingInterval'.
     */
    public void setClientPingInterval(int clientPingInterval)
    {
        this._clientPingInterval = clientPingInterval;
        this._has_clientPingInterval = true;
    } //-- void setClientPingInterval(int) 

    /**
     * Sets the value of field 'embeddedRegistry'. The field
     * 'embeddedRegistry' has the following description: This
     * specifies whether to use an embedded (or internal) RMI
     *  registry, or an external one.
     *  
     * 
     * @param embeddedRegistry the value of field 'embeddedRegistry'
     */
    public void setEmbeddedRegistry(boolean embeddedRegistry)
    {
        this._embeddedRegistry = embeddedRegistry;
        this._has_embeddedRegistry = true;
    } //-- void setEmbeddedRegistry(boolean) 

    /**
     * Sets the value of field 'registryHost'. The field
     * 'registryHost' has the following description: The RMI
     * registry host. This is only applicable if an external
     *  RMI registry is being used, i.e., embeddedRegistry is
     * false.
     *  
     * 
     * @param registryHost the value of field 'registryHost'.
     */
    public void setRegistryHost(java.lang.String registryHost)
    {
        this._registryHost = registryHost;
    } //-- void setRegistryHost(java.lang.String) 

    /**
     * Sets the value of field 'registryPort'. The field
     * 'registryPort' has the following description: The RMI
     * registry port.
     *  
     * 
     * @param registryPort the value of field 'registryPort'.
     */
    public void setRegistryPort(int registryPort)
    {
        this._registryPort = registryPort;
        this._has_registryPort = true;
    } //-- void setRegistryPort(int) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static org.exolab.jms.config.RmiConfiguration unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (org.exolab.jms.config.RmiConfiguration) Unmarshaller.unmarshal(org.exolab.jms.config.RmiConfiguration.class, reader);
    } //-- org.exolab.jms.config.RmiConfiguration unmarshal(java.io.Reader) 

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
