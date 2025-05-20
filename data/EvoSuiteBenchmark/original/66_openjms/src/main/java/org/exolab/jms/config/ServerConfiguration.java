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
 * This element defines items specific to the OpenJMS server.
 *  By default, OpenJMS uses an embedded JNDI server.
 *  
 * 
 * @version $Revision$ $Date$
 */
public class ServerConfiguration implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The host address of the OpenJMS server. This will typically
     * be
     *  'localhost'. If the host machine has multiple NICs, then 
     *  the host will need to be specified.
     *  
     */
    private java.lang.String _host = "localhost";

    /**
     * This specifies whether to use an embedded (or internal) JNDI
     * 
     *  provider, or an external one. If false, then the 
     *  JndiConfiguration element is relevant. For an embedded
     * JNDI,
     *  the JndiConfiguration element will be populated with
     * values.
     *  
     */
    private boolean _embeddedJNDI = true;

    /**
     * keeps track of state for field: _embeddedJNDI
     */
    private boolean _has_embeddedJNDI;

    /**
     * The maximum no. of threads to use, when handling concurrent
     *  requests. These are shared across all client connections.
     *  
     */
    private int _maxThreads = 100;

    /**
     * keeps track of state for field: _maxThreads
     */
    private boolean _has_maxThreads;


      //----------------/
     //- Constructors -/
    //----------------/

    public ServerConfiguration() {
        super();
        setHost("localhost");
    } //-- org.exolab.jms.config.ServerConfiguration()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method deleteEmbeddedJNDI
     */
    public void deleteEmbeddedJNDI()
    {
        this._has_embeddedJNDI= false;
    } //-- void deleteEmbeddedJNDI() 

    /**
     * Method deleteMaxThreads
     */
    public void deleteMaxThreads()
    {
        this._has_maxThreads= false;
    } //-- void deleteMaxThreads() 

    /**
     * Returns the value of field 'embeddedJNDI'. The field
     * 'embeddedJNDI' has the following description: This specifies
     * whether to use an embedded (or internal) JNDI 
     *  provider, or an external one. If false, then the 
     *  JndiConfiguration element is relevant. For an embedded
     * JNDI,
     *  the JndiConfiguration element will be populated with
     * values.
     *  
     * 
     * @return the value of field 'embeddedJNDI'.
     */
    public boolean getEmbeddedJNDI()
    {
        return this._embeddedJNDI;
    } //-- boolean getEmbeddedJNDI() 

    /**
     * Returns the value of field 'host'. The field 'host' has the
     * following description: The host address of the OpenJMS
     * server. This will typically be
     *  'localhost'. If the host machine has multiple NICs, then 
     *  the host will need to be specified.
     *  
     * 
     * @return the value of field 'host'.
     */
    public java.lang.String getHost()
    {
        return this._host;
    } //-- java.lang.String getHost() 

    /**
     * Returns the value of field 'maxThreads'. The field
     * 'maxThreads' has the following description: The maximum no.
     * of threads to use, when handling concurrent
     *  requests. These are shared across all client connections.
     *  
     * 
     * @return the value of field 'maxThreads'.
     */
    public int getMaxThreads()
    {
        return this._maxThreads;
    } //-- int getMaxThreads() 

    /**
     * Method hasEmbeddedJNDI
     */
    public boolean hasEmbeddedJNDI()
    {
        return this._has_embeddedJNDI;
    } //-- boolean hasEmbeddedJNDI() 

    /**
     * Method hasMaxThreads
     */
    public boolean hasMaxThreads()
    {
        return this._has_maxThreads;
    } //-- boolean hasMaxThreads() 

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
     * Sets the value of field 'embeddedJNDI'. The field
     * 'embeddedJNDI' has the following description: This specifies
     * whether to use an embedded (or internal) JNDI 
     *  provider, or an external one. If false, then the 
     *  JndiConfiguration element is relevant. For an embedded
     * JNDI,
     *  the JndiConfiguration element will be populated with
     * values.
     *  
     * 
     * @param embeddedJNDI the value of field 'embeddedJNDI'.
     */
    public void setEmbeddedJNDI(boolean embeddedJNDI)
    {
        this._embeddedJNDI = embeddedJNDI;
        this._has_embeddedJNDI = true;
    } //-- void setEmbeddedJNDI(boolean) 

    /**
     * Sets the value of field 'host'. The field 'host' has the
     * following description: The host address of the OpenJMS
     * server. This will typically be
     *  'localhost'. If the host machine has multiple NICs, then 
     *  the host will need to be specified.
     *  
     * 
     * @param host the value of field 'host'.
     */
    public void setHost(java.lang.String host)
    {
        this._host = host;
    } //-- void setHost(java.lang.String) 

    /**
     * Sets the value of field 'maxThreads'. The field 'maxThreads'
     * has the following description: The maximum no. of threads to
     * use, when handling concurrent
     *  requests. These are shared across all client connections.
     *  
     * 
     * @param maxThreads the value of field 'maxThreads'.
     */
    public void setMaxThreads(int maxThreads)
    {
        this._maxThreads = maxThreads;
        this._has_maxThreads = true;
    } //-- void setMaxThreads(int) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static org.exolab.jms.config.ServerConfiguration unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (org.exolab.jms.config.ServerConfiguration) Unmarshaller.unmarshal(org.exolab.jms.config.ServerConfiguration.class, reader);
    } //-- org.exolab.jms.config.ServerConfiguration unmarshal(java.io.Reader) 

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
