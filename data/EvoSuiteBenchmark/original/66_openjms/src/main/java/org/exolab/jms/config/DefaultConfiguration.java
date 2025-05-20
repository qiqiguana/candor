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
 * This top level element defines the elements which do not need to
 *  be configured by users, but are required by OpenJMS.
 *  Where an element is not provided by Configuration, the default
 * values
 *  provided by this element will be used.
 *  
 * 
 * @version $Revision$ $Date$
 */
public class DefaultConfiguration implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * This element defines items specific to the OpenJMS server.
     *  By default, OpenJMS uses an embedded JNDI server.
     *  
     */
    private org.exolab.jms.config.ServerConfiguration _serverConfiguration;

    /**
     * Field _connectors
     */
    private org.exolab.jms.config.Connectors _connectors;

    /**
     * This element defines items specific to the TCP connector.
     *  
     */
    private org.exolab.jms.config.TcpConfiguration _tcpConfiguration;

    /**
     * This element defines items specific to the TCPS connector.
     *  
     */
    private org.exolab.jms.config.TcpsConfiguration _tcpsConfiguration;

    /**
     * This element defines items specific to the RMI connector.
     *  
     */
    private org.exolab.jms.config.RmiConfiguration _rmiConfiguration;

    /**
     * This element defines items specific to the HTTP
     * configuration.
     *  
     */
    private org.exolab.jms.config.HttpConfiguration _httpConfiguration;

    /**
     * This element defines items specific to the HTTPS
     * configuration.
     *  
     */
    private org.exolab.jms.config.HttpsConfiguration _httpsConfiguration;

    /**
     * This element defines items specific to the message manager.
     *  
     */
    private org.exolab.jms.config.MessageManagerConfiguration _messageManagerConfiguration;

    /**
     * This element defines items specific to the scheduler.
     *  
     */
    private org.exolab.jms.config.SchedulerConfiguration _schedulerConfiguration;

    /**
     * This element defines items specific to the garbage
     * collection service.
     *  
     */
    private org.exolab.jms.config.GarbageCollectionConfiguration _garbageCollectionConfiguration;

    /**
     * This element specifies the file to initialise logging with.
     * OpenJMS
     *  uses Apache log4j
     * (http://jakarta.apache.org/log4j/docs/index.html) 
     *  for logging.
     *  
     */
    private org.exolab.jms.config.LoggerConfiguration _loggerConfiguration;

    /**
     * This element defines items specific to the OpenJMS server.
     *  By default, OpenJMS uses no security.
     *  
     */
    private org.exolab.jms.config.SecurityConfiguration _securityConfiguration;


      //----------------/
     //- Constructors -/
    //----------------/

    public DefaultConfiguration() {
        super();
    } //-- org.exolab.jms.config.DefaultConfiguration()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'connectors'.
     * 
     * @return the value of field 'connectors'.
     */
    public org.exolab.jms.config.Connectors getConnectors()
    {
        return this._connectors;
    } //-- org.exolab.jms.config.Connectors getConnectors() 

    /**
     * Returns the value of field 'garbageCollectionConfiguration'.
     * The field 'garbageCollectionConfiguration' has the following
     * description: This element defines items specific to the
     * garbage collection service.
     *  
     * 
     * @return the value of field 'garbageCollectionConfiguration'.
     */
    public org.exolab.jms.config.GarbageCollectionConfiguration getGarbageCollectionConfiguration()
    {
        return this._garbageCollectionConfiguration;
    } //-- org.exolab.jms.config.GarbageCollectionConfiguration getGarbageCollectionConfiguration() 

    /**
     * Returns the value of field 'httpConfiguration'. The field
     * 'httpConfiguration' has the following description: This
     * element defines items specific to the HTTP configuration.
     *  
     * 
     * @return the value of field 'httpConfiguration'.
     */
    public org.exolab.jms.config.HttpConfiguration getHttpConfiguration()
    {
        return this._httpConfiguration;
    } //-- org.exolab.jms.config.HttpConfiguration getHttpConfiguration() 

    /**
     * Returns the value of field 'httpsConfiguration'. The field
     * 'httpsConfiguration' has the following description: This
     * element defines items specific to the HTTPS configuration.
     *  
     * 
     * @return the value of field 'httpsConfiguration'.
     */
    public org.exolab.jms.config.HttpsConfiguration getHttpsConfiguration()
    {
        return this._httpsConfiguration;
    } //-- org.exolab.jms.config.HttpsConfiguration getHttpsConfiguration() 

    /**
     * Returns the value of field 'loggerConfiguration'. The field
     * 'loggerConfiguration' has the following description: This
     * element specifies the file to initialise logging with.
     * OpenJMS
     *  uses Apache log4j
     * (http://jakarta.apache.org/log4j/docs/index.html) 
     *  for logging.
     *  
     * 
     * @return the value of field 'loggerConfiguration'.
     */
    public org.exolab.jms.config.LoggerConfiguration getLoggerConfiguration()
    {
        return this._loggerConfiguration;
    } //-- org.exolab.jms.config.LoggerConfiguration getLoggerConfiguration() 

    /**
     * Returns the value of field 'messageManagerConfiguration'.
     * The field 'messageManagerConfiguration' has the following
     * description: This element defines items specific to the
     * message manager.
     *  
     * 
     * @return the value of field 'messageManagerConfiguration'.
     */
    public org.exolab.jms.config.MessageManagerConfiguration getMessageManagerConfiguration()
    {
        return this._messageManagerConfiguration;
    } //-- org.exolab.jms.config.MessageManagerConfiguration getMessageManagerConfiguration() 

    /**
     * Returns the value of field 'rmiConfiguration'. The field
     * 'rmiConfiguration' has the following description: This
     * element defines items specific to the RMI connector.
     *  
     * 
     * @return the value of field 'rmiConfiguration'.
     */
    public org.exolab.jms.config.RmiConfiguration getRmiConfiguration()
    {
        return this._rmiConfiguration;
    } //-- org.exolab.jms.config.RmiConfiguration getRmiConfiguration() 

    /**
     * Returns the value of field 'schedulerConfiguration'. The
     * field 'schedulerConfiguration' has the following
     * description: This element defines items specific to the
     * scheduler.
     *  
     * 
     * @return the value of field 'schedulerConfiguration'.
     */
    public org.exolab.jms.config.SchedulerConfiguration getSchedulerConfiguration()
    {
        return this._schedulerConfiguration;
    } //-- org.exolab.jms.config.SchedulerConfiguration getSchedulerConfiguration() 

    /**
     * Returns the value of field 'securityConfiguration'. The
     * field 'securityConfiguration' has the following description:
     * This element defines items specific to the OpenJMS server.
     *  By default, OpenJMS uses no security.
     *  
     * 
     * @return the value of field 'securityConfiguration'.
     */
    public org.exolab.jms.config.SecurityConfiguration getSecurityConfiguration()
    {
        return this._securityConfiguration;
    } //-- org.exolab.jms.config.SecurityConfiguration getSecurityConfiguration() 

    /**
     * Returns the value of field 'serverConfiguration'. The field
     * 'serverConfiguration' has the following description: This
     * element defines items specific to the OpenJMS server.
     *  By default, OpenJMS uses an embedded JNDI server.
     *  
     * 
     * @return the value of field 'serverConfiguration'.
     */
    public org.exolab.jms.config.ServerConfiguration getServerConfiguration()
    {
        return this._serverConfiguration;
    } //-- org.exolab.jms.config.ServerConfiguration getServerConfiguration() 

    /**
     * Returns the value of field 'tcpConfiguration'. The field
     * 'tcpConfiguration' has the following description: This
     * element defines items specific to the TCP connector.
     *  
     * 
     * @return the value of field 'tcpConfiguration'.
     */
    public org.exolab.jms.config.TcpConfiguration getTcpConfiguration()
    {
        return this._tcpConfiguration;
    } //-- org.exolab.jms.config.TcpConfiguration getTcpConfiguration() 

    /**
     * Returns the value of field 'tcpsConfiguration'. The field
     * 'tcpsConfiguration' has the following description: This
     * element defines items specific to the TCPS connector.
     *  
     * 
     * @return the value of field 'tcpsConfiguration'.
     */
    public org.exolab.jms.config.TcpsConfiguration getTcpsConfiguration()
    {
        return this._tcpsConfiguration;
    } //-- org.exolab.jms.config.TcpsConfiguration getTcpsConfiguration() 

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
     * Sets the value of field 'connectors'.
     * 
     * @param connectors the value of field 'connectors'.
     */
    public void setConnectors(org.exolab.jms.config.Connectors connectors)
    {
        this._connectors = connectors;
    } //-- void setConnectors(org.exolab.jms.config.Connectors) 

    /**
     * Sets the value of field 'garbageCollectionConfiguration'.
     * The field 'garbageCollectionConfiguration' has the following
     * description: This element defines items specific to the
     * garbage collection service.
     *  
     * 
     * @param garbageCollectionConfiguration the value of field
     * 'garbageCollectionConfiguration'.
     */
    public void setGarbageCollectionConfiguration(org.exolab.jms.config.GarbageCollectionConfiguration garbageCollectionConfiguration)
    {
        this._garbageCollectionConfiguration = garbageCollectionConfiguration;
    } //-- void setGarbageCollectionConfiguration(org.exolab.jms.config.GarbageCollectionConfiguration) 

    /**
     * Sets the value of field 'httpConfiguration'. The field
     * 'httpConfiguration' has the following description: This
     * element defines items specific to the HTTP configuration.
     *  
     * 
     * @param httpConfiguration the value of field
     * 'httpConfiguration'.
     */
    public void setHttpConfiguration(org.exolab.jms.config.HttpConfiguration httpConfiguration)
    {
        this._httpConfiguration = httpConfiguration;
    } //-- void setHttpConfiguration(org.exolab.jms.config.HttpConfiguration) 

    /**
     * Sets the value of field 'httpsConfiguration'. The field
     * 'httpsConfiguration' has the following description: This
     * element defines items specific to the HTTPS configuration.
     *  
     * 
     * @param httpsConfiguration the value of field
     * 'httpsConfiguration'.
     */
    public void setHttpsConfiguration(org.exolab.jms.config.HttpsConfiguration httpsConfiguration)
    {
        this._httpsConfiguration = httpsConfiguration;
    } //-- void setHttpsConfiguration(org.exolab.jms.config.HttpsConfiguration) 

    /**
     * Sets the value of field 'loggerConfiguration'. The field
     * 'loggerConfiguration' has the following description: This
     * element specifies the file to initialise logging with.
     * OpenJMS
     *  uses Apache log4j
     * (http://jakarta.apache.org/log4j/docs/index.html) 
     *  for logging.
     *  
     * 
     * @param loggerConfiguration the value of field
     * 'loggerConfiguration'.
     */
    public void setLoggerConfiguration(org.exolab.jms.config.LoggerConfiguration loggerConfiguration)
    {
        this._loggerConfiguration = loggerConfiguration;
    } //-- void setLoggerConfiguration(org.exolab.jms.config.LoggerConfiguration) 

    /**
     * Sets the value of field 'messageManagerConfiguration'. The
     * field 'messageManagerConfiguration' has the following
     * description: This element defines items specific to the
     * message manager.
     *  
     * 
     * @param messageManagerConfiguration the value of field
     * 'messageManagerConfiguration'.
     */
    public void setMessageManagerConfiguration(org.exolab.jms.config.MessageManagerConfiguration messageManagerConfiguration)
    {
        this._messageManagerConfiguration = messageManagerConfiguration;
    } //-- void setMessageManagerConfiguration(org.exolab.jms.config.MessageManagerConfiguration) 

    /**
     * Sets the value of field 'rmiConfiguration'. The field
     * 'rmiConfiguration' has the following description: This
     * element defines items specific to the RMI connector.
     *  
     * 
     * @param rmiConfiguration the value of field 'rmiConfiguration'
     */
    public void setRmiConfiguration(org.exolab.jms.config.RmiConfiguration rmiConfiguration)
    {
        this._rmiConfiguration = rmiConfiguration;
    } //-- void setRmiConfiguration(org.exolab.jms.config.RmiConfiguration) 

    /**
     * Sets the value of field 'schedulerConfiguration'. The field
     * 'schedulerConfiguration' has the following description: This
     * element defines items specific to the scheduler.
     *  
     * 
     * @param schedulerConfiguration the value of field
     * 'schedulerConfiguration'.
     */
    public void setSchedulerConfiguration(org.exolab.jms.config.SchedulerConfiguration schedulerConfiguration)
    {
        this._schedulerConfiguration = schedulerConfiguration;
    } //-- void setSchedulerConfiguration(org.exolab.jms.config.SchedulerConfiguration) 

    /**
     * Sets the value of field 'securityConfiguration'. The field
     * 'securityConfiguration' has the following description: This
     * element defines items specific to the OpenJMS server.
     *  By default, OpenJMS uses no security.
     *  
     * 
     * @param securityConfiguration the value of field
     * 'securityConfiguration'.
     */
    public void setSecurityConfiguration(org.exolab.jms.config.SecurityConfiguration securityConfiguration)
    {
        this._securityConfiguration = securityConfiguration;
    } //-- void setSecurityConfiguration(org.exolab.jms.config.SecurityConfiguration) 

    /**
     * Sets the value of field 'serverConfiguration'. The field
     * 'serverConfiguration' has the following description: This
     * element defines items specific to the OpenJMS server.
     *  By default, OpenJMS uses an embedded JNDI server.
     *  
     * 
     * @param serverConfiguration the value of field
     * 'serverConfiguration'.
     */
    public void setServerConfiguration(org.exolab.jms.config.ServerConfiguration serverConfiguration)
    {
        this._serverConfiguration = serverConfiguration;
    } //-- void setServerConfiguration(org.exolab.jms.config.ServerConfiguration) 

    /**
     * Sets the value of field 'tcpConfiguration'. The field
     * 'tcpConfiguration' has the following description: This
     * element defines items specific to the TCP connector.
     *  
     * 
     * @param tcpConfiguration the value of field 'tcpConfiguration'
     */
    public void setTcpConfiguration(org.exolab.jms.config.TcpConfiguration tcpConfiguration)
    {
        this._tcpConfiguration = tcpConfiguration;
    } //-- void setTcpConfiguration(org.exolab.jms.config.TcpConfiguration) 

    /**
     * Sets the value of field 'tcpsConfiguration'. The field
     * 'tcpsConfiguration' has the following description: This
     * element defines items specific to the TCPS connector.
     *  
     * 
     * @param tcpsConfiguration the value of field
     * 'tcpsConfiguration'.
     */
    public void setTcpsConfiguration(org.exolab.jms.config.TcpsConfiguration tcpsConfiguration)
    {
        this._tcpsConfiguration = tcpsConfiguration;
    } //-- void setTcpsConfiguration(org.exolab.jms.config.TcpsConfiguration) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static org.exolab.jms.config.DefaultConfiguration unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (org.exolab.jms.config.DefaultConfiguration) Unmarshaller.unmarshal(org.exolab.jms.config.DefaultConfiguration.class, reader);
    } //-- org.exolab.jms.config.DefaultConfiguration unmarshal(java.io.Reader) 

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
