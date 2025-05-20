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

import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;

/**
 * This type defines items specific to socket based connectors.
 *  
 * 
 * @version $Revision$ $Date$
 */
public abstract class SocketConfigurationType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The port number that the server accepts connections on.
     *  
     */
    private int _port;

    /**
     * keeps track of state for field: _port
     */
    private boolean _has_port;

    /**
     * The JNDI port, if an embedded JNDI provider is being used.
     *  A value of zero indicates to use the same as that of the
     * port
     *  attribute.
     *  
     */
    private int _jndiPort = 0;

    /**
     * keeps track of state for field: _jndiPort
     */
    private boolean _has_jndiPort;

    /**
     * The administration service port.
     *  A value of zero indicates to use the same as that of the
     * port
     *  attribute.
     *  
     */
    private int _adminPort = 0;

    /**
     * keeps track of state for field: _adminPort
     */
    private boolean _has_adminPort;

    /**
     * Can be used on a multi-homed host to only accept connect
     *  requests on the address specified by
     * ServerConfiguration/host.
     *  If true, it will accept connections on any/all local
     * addresses.
     *  
     */
    private boolean _bindAll = true;

    /**
     * keeps track of state for field: _bindAll
     */
    private boolean _has_bindAll;


      //----------------/
     //- Constructors -/
    //----------------/

    public SocketConfigurationType() {
        super();
    } //-- org.exolab.jms.config.SocketConfigurationType()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method deleteAdminPort
     */
    public void deleteAdminPort()
    {
        this._has_adminPort= false;
    } //-- void deleteAdminPort() 

    /**
     * Method deleteBindAll
     */
    public void deleteBindAll()
    {
        this._has_bindAll= false;
    } //-- void deleteBindAll() 

    /**
     * Method deleteJndiPort
     */
    public void deleteJndiPort()
    {
        this._has_jndiPort= false;
    } //-- void deleteJndiPort() 

    /**
     * Returns the value of field 'adminPort'. The field
     * 'adminPort' has the following description: The
     * administration service port.
     *  A value of zero indicates to use the same as that of the
     * port
     *  attribute.
     *  
     * 
     * @return the value of field 'adminPort'.
     */
    public int getAdminPort()
    {
        return this._adminPort;
    } //-- int getAdminPort() 

    /**
     * Returns the value of field 'bindAll'. The field 'bindAll'
     * has the following description: Can be used on a multi-homed
     * host to only accept connect
     *  requests on the address specified by
     * ServerConfiguration/host.
     *  If true, it will accept connections on any/all local
     * addresses.
     *  
     * 
     * @return the value of field 'bindAll'.
     */
    public boolean getBindAll()
    {
        return this._bindAll;
    } //-- boolean getBindAll() 

    /**
     * Returns the value of field 'jndiPort'. The field 'jndiPort'
     * has the following description: The JNDI port, if an embedded
     * JNDI provider is being used.
     *  A value of zero indicates to use the same as that of the
     * port
     *  attribute.
     *  
     * 
     * @return the value of field 'jndiPort'.
     */
    public int getJndiPort()
    {
        return this._jndiPort;
    } //-- int getJndiPort() 

    /**
     * Returns the value of field 'port'. The field 'port' has the
     * following description: The port number that the server
     * accepts connections on.
     *  
     * 
     * @return the value of field 'port'.
     */
    public int getPort()
    {
        return this._port;
    } //-- int getPort() 

    /**
     * Method hasAdminPort
     */
    public boolean hasAdminPort()
    {
        return this._has_adminPort;
    } //-- boolean hasAdminPort() 

    /**
     * Method hasBindAll
     */
    public boolean hasBindAll()
    {
        return this._has_bindAll;
    } //-- boolean hasBindAll() 

    /**
     * Method hasJndiPort
     */
    public boolean hasJndiPort()
    {
        return this._has_jndiPort;
    } //-- boolean hasJndiPort() 

    /**
     * Method hasPort
     */
    public boolean hasPort()
    {
        return this._has_port;
    } //-- boolean hasPort() 

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
     * Sets the value of field 'adminPort'. The field 'adminPort'
     * has the following description: The administration service
     * port.
     *  A value of zero indicates to use the same as that of the
     * port
     *  attribute.
     *  
     * 
     * @param adminPort the value of field 'adminPort'.
     */
    public void setAdminPort(int adminPort)
    {
        this._adminPort = adminPort;
        this._has_adminPort = true;
    } //-- void setAdminPort(int) 

    /**
     * Sets the value of field 'bindAll'. The field 'bindAll' has
     * the following description: Can be used on a multi-homed host
     * to only accept connect
     *  requests on the address specified by
     * ServerConfiguration/host.
     *  If true, it will accept connections on any/all local
     * addresses.
     *  
     * 
     * @param bindAll the value of field 'bindAll'.
     */
    public void setBindAll(boolean bindAll)
    {
        this._bindAll = bindAll;
        this._has_bindAll = true;
    } //-- void setBindAll(boolean) 

    /**
     * Sets the value of field 'jndiPort'. The field 'jndiPort' has
     * the following description: The JNDI port, if an embedded
     * JNDI provider is being used.
     *  A value of zero indicates to use the same as that of the
     * port
     *  attribute.
     *  
     * 
     * @param jndiPort the value of field 'jndiPort'.
     */
    public void setJndiPort(int jndiPort)
    {
        this._jndiPort = jndiPort;
        this._has_jndiPort = true;
    } //-- void setJndiPort(int) 

    /**
     * Sets the value of field 'port'. The field 'port' has the
     * following description: The port number that the server
     * accepts connections on.
     *  
     * 
     * @param port the value of field 'port'.
     */
    public void setPort(int port)
    {
        this._port = port;
        this._has_port = true;
    } //-- void setPort(int) 

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
