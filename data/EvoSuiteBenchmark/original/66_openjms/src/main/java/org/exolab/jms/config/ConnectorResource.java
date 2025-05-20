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
import org.exolab.jms.config.types.SchemeType;
import org.xml.sax.ContentHandler;

/**
 * Class ConnectorResource.
 * 
 * @version $Revision$ $Date$
 */
public class ConnectorResource implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _scheme
     */
    private org.exolab.jms.config.types.SchemeType _scheme;

    /**
     * Field _description
     */
    private java.lang.String _description;

    /**
     * Field _server
     */
    private org.exolab.jms.config.Server _server;

    /**
     * Field _jndi
     */
    private org.exolab.jms.config.Jndi _jndi;


      //----------------/
     //- Constructors -/
    //----------------/

    public ConnectorResource() {
        super();
    } //-- org.exolab.jms.config.ConnectorResource()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'description'.
     * 
     * @return the value of field 'description'.
     */
    public java.lang.String getDescription()
    {
        return this._description;
    } //-- java.lang.String getDescription() 

    /**
     * Returns the value of field 'jndi'.
     * 
     * @return the value of field 'jndi'.
     */
    public org.exolab.jms.config.Jndi getJndi()
    {
        return this._jndi;
    } //-- org.exolab.jms.config.Jndi getJndi() 

    /**
     * Returns the value of field 'scheme'.
     * 
     * @return the value of field 'scheme'.
     */
    public org.exolab.jms.config.types.SchemeType getScheme()
    {
        return this._scheme;
    } //-- org.exolab.jms.config.types.SchemeType getScheme() 

    /**
     * Returns the value of field 'server'.
     * 
     * @return the value of field 'server'.
     */
    public org.exolab.jms.config.Server getServer()
    {
        return this._server;
    } //-- org.exolab.jms.config.Server getServer() 

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
     * Sets the value of field 'description'.
     * 
     * @param description the value of field 'description'.
     */
    public void setDescription(java.lang.String description)
    {
        this._description = description;
    } //-- void setDescription(java.lang.String) 

    /**
     * Sets the value of field 'jndi'.
     * 
     * @param jndi the value of field 'jndi'.
     */
    public void setJndi(org.exolab.jms.config.Jndi jndi)
    {
        this._jndi = jndi;
    } //-- void setJndi(org.exolab.jms.config.Jndi) 

    /**
     * Sets the value of field 'scheme'.
     * 
     * @param scheme the value of field 'scheme'.
     */
    public void setScheme(org.exolab.jms.config.types.SchemeType scheme)
    {
        this._scheme = scheme;
    } //-- void setScheme(org.exolab.jms.config.types.SchemeType) 

    /**
     * Sets the value of field 'server'.
     * 
     * @param server the value of field 'server'.
     */
    public void setServer(org.exolab.jms.config.Server server)
    {
        this._server = server;
    } //-- void setServer(org.exolab.jms.config.Server) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static org.exolab.jms.config.ConnectorResource unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (org.exolab.jms.config.ConnectorResource) Unmarshaller.unmarshal(org.exolab.jms.config.ConnectorResource.class, reader);
    } //-- org.exolab.jms.config.ConnectorResource unmarshal(java.io.Reader) 

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
