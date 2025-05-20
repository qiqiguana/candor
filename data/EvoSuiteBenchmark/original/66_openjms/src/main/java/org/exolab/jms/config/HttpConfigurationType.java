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
 * This type defines items specific to HTTP and HTTPS connectors.
 *  
 * 
 * @version $Revision$ $Date$
 */
public abstract class HttpConfigurationType extends org.exolab.jms.config.SocketConfigurationType 
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The web server host.
     *  
     */
    private java.lang.String _webServerHost = "localhost";

    /**
     * The web server port.
     *  
     */
    private int _webServerPort;

    /**
     * keeps track of state for field: _webServerPort
     */
    private boolean _has_webServerPort;

    /**
     * The tunnel servlet path
     *  
     */
    private java.lang.String _servlet = "/openjms-tunnel/tunnel";


      //----------------/
     //- Constructors -/
    //----------------/

    public HttpConfigurationType() {
        super();
        setWebServerHost("localhost");
        setServlet("/openjms-tunnel/tunnel");
    } //-- org.exolab.jms.config.HttpConfigurationType()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'servlet'. The field 'servlet'
     * has the following description: The tunnel servlet path
     *  
     * 
     * @return the value of field 'servlet'.
     */
    public java.lang.String getServlet()
    {
        return this._servlet;
    } //-- java.lang.String getServlet() 

    /**
     * Returns the value of field 'webServerHost'. The field
     * 'webServerHost' has the following description: The web
     * server host.
     *  
     * 
     * @return the value of field 'webServerHost'.
     */
    public java.lang.String getWebServerHost()
    {
        return this._webServerHost;
    } //-- java.lang.String getWebServerHost() 

    /**
     * Returns the value of field 'webServerPort'. The field
     * 'webServerPort' has the following description: The web
     * server port.
     *  
     * 
     * @return the value of field 'webServerPort'.
     */
    public int getWebServerPort()
    {
        return this._webServerPort;
    } //-- int getWebServerPort() 

    /**
     * Method hasWebServerPort
     */
    public boolean hasWebServerPort()
    {
        return this._has_webServerPort;
    } //-- boolean hasWebServerPort() 

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
     * Sets the value of field 'servlet'. The field 'servlet' has
     * the following description: The tunnel servlet path
     *  
     * 
     * @param servlet the value of field 'servlet'.
     */
    public void setServlet(java.lang.String servlet)
    {
        this._servlet = servlet;
    } //-- void setServlet(java.lang.String) 

    /**
     * Sets the value of field 'webServerHost'. The field
     * 'webServerHost' has the following description: The web
     * server host.
     *  
     * 
     * @param webServerHost the value of field 'webServerHost'.
     */
    public void setWebServerHost(java.lang.String webServerHost)
    {
        this._webServerHost = webServerHost;
    } //-- void setWebServerHost(java.lang.String) 

    /**
     * Sets the value of field 'webServerPort'. The field
     * 'webServerPort' has the following description: The web
     * server port.
     *  
     * 
     * @param webServerPort the value of field 'webServerPort'.
     */
    public void setWebServerPort(int webServerPort)
    {
        this._webServerPort = webServerPort;
        this._has_webServerPort = true;
    } //-- void setWebServerPort(int) 

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
