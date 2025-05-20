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
 * This element defines items specific to OpenJMS administration.
 *  
 * 
 * @version $Revision$ $Date$
 */
public class AdminConfiguration implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The path of the script to start the OpenJMS server.
     *  
     */
    private java.lang.String _script;

    /**
     * The XML configuration file to run the OpenJMS server with.
     *  
     */
    private java.lang.String _config;


      //----------------/
     //- Constructors -/
    //----------------/

    public AdminConfiguration() {
        super();
    } //-- org.exolab.jms.config.AdminConfiguration()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'config'. The field 'config' has
     * the following description: The XML configuration file to run
     * the OpenJMS server with.
     *  
     * 
     * @return the value of field 'config'.
     */
    public java.lang.String getConfig()
    {
        return this._config;
    } //-- java.lang.String getConfig() 

    /**
     * Returns the value of field 'script'. The field 'script' has
     * the following description: The path of the script to start
     * the OpenJMS server.
     *  
     * 
     * @return the value of field 'script'.
     */
    public java.lang.String getScript()
    {
        return this._script;
    } //-- java.lang.String getScript() 

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
     * Sets the value of field 'config'. The field 'config' has the
     * following description: The XML configuration file to run the
     * OpenJMS server with.
     *  
     * 
     * @param config the value of field 'config'.
     */
    public void setConfig(java.lang.String config)
    {
        this._config = config;
    } //-- void setConfig(java.lang.String) 

    /**
     * Sets the value of field 'script'. The field 'script' has the
     * following description: The path of the script to start the
     * OpenJMS server.
     *  
     * 
     * @param script the value of field 'script'.
     */
    public void setScript(java.lang.String script)
    {
        this._script = script;
    } //-- void setScript(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static org.exolab.jms.config.AdminConfiguration unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (org.exolab.jms.config.AdminConfiguration) Unmarshaller.unmarshal(org.exolab.jms.config.AdminConfiguration.class, reader);
    } //-- org.exolab.jms.config.AdminConfiguration unmarshal(java.io.Reader) 

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
