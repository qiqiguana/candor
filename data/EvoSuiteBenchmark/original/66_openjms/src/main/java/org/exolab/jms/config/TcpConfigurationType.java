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
 * This type defines items specific to TCP and TCPS connectors.
 *  
 * 
 * @version $Revision$ $Date$
 */
public abstract class TcpConfigurationType extends org.exolab.jms.config.SocketConfigurationType 
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * This is only applicable when the server is behind a NAT
     * firewall.
     *  This becomes the internal address the server is known by
     * and the
     *  host address in ServerConfiguration is the external
     * address.
     *  Clients will attempt to connect to ServerConfiguration/host
     *  first. If that fails, they will try to connect to
     * internalHost
     *  
     */
    private java.lang.String _internalHost;


      //----------------/
     //- Constructors -/
    //----------------/

    public TcpConfigurationType() {
        super();
    } //-- org.exolab.jms.config.TcpConfigurationType()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'internalHost'. The field
     * 'internalHost' has the following description: This is only
     * applicable when the server is behind a NAT firewall.
     *  This becomes the internal address the server is known by
     * and the
     *  host address in ServerConfiguration is the external
     * address.
     *  Clients will attempt to connect to ServerConfiguration/host
     *  first. If that fails, they will try to connect to
     * internalHost
     *  
     * 
     * @return the value of field 'internalHost'.
     */
    public java.lang.String getInternalHost()
    {
        return this._internalHost;
    } //-- java.lang.String getInternalHost() 

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
     * Sets the value of field 'internalHost'. The field
     * 'internalHost' has the following description: This is only
     * applicable when the server is behind a NAT firewall.
     *  This becomes the internal address the server is known by
     * and the
     *  host address in ServerConfiguration is the external
     * address.
     *  Clients will attempt to connect to ServerConfiguration/host
     *  first. If that fails, they will try to connect to
     * internalHost
     *  
     * 
     * @param internalHost the value of field 'internalHost'.
     */
    public void setInternalHost(java.lang.String internalHost)
    {
        this._internalHost = internalHost;
    } //-- void setInternalHost(java.lang.String) 

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
